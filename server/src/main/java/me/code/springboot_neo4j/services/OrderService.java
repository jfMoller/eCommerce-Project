package me.code.springboot_neo4j.services;

import jakarta.transaction.Transactional;
import me.code.springboot_neo4j.dto.response.entity.OngoingOrder;
import me.code.springboot_neo4j.dto.response.entity.PlacedOrder;
import me.code.springboot_neo4j.dto.response.entity.UnavailableProduct;
import me.code.springboot_neo4j.dto.response.error.detailvariant.OrderErrorDetail;
import me.code.springboot_neo4j.dto.response.success.Success;
import me.code.springboot_neo4j.exceptions.types.CustomRuntimeException;
import me.code.springboot_neo4j.exceptions.types.variant.OrderException;
import me.code.springboot_neo4j.models.nodes.Order;
import me.code.springboot_neo4j.models.nodes.Product;
import me.code.springboot_neo4j.models.nodes.ProductDetails;
import me.code.springboot_neo4j.models.nodes.User;
import me.code.springboot_neo4j.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final ProductDetailsService productDetailsService;

    @Autowired
    public OrderService(
            OrderRepository orderRepository,
            ProductService productService,
            ProductDetailsService productDetailsService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.productDetailsService = productDetailsService;
    }

    @Transactional
    public Success placeOrder(User user, String[] productIds) {
        try {
            List<Product> orderedProducts = Arrays.stream(productIds)
                    .map(productService::loadProductById)
                    .toList();

            List<ProductDetails> productDetails =
                    productDetailsService.generateProductDetails(orderedProducts);

            List<UnavailableProduct> unavailableProducts =
                    productDetailsService.findUnavailableProducts(productDetails);

            if (hasUnavailableProducts(unavailableProducts)) {
                throw new OrderException(
                        HttpStatus.BAD_REQUEST,
                        "Could not place order",
                        new OrderErrorDetail(
                                "The order contains unavailable products",
                                unavailableProducts));
            }

            orderRepository.save(new Order(user, productDetails));

            return new Success(HttpStatus.OK, "The order was placed successfully");

        } catch (OrderException exception) {
            throw new OrderException(exception.getStatus(), exception.getMessage(), exception.getOrderErrorDetail());

        } catch (Exception exception) {
            throw new CustomRuntimeException(HttpStatus.BAD_REQUEST, "Could not place order");
        }
    }

    private boolean hasUnavailableProducts(List<UnavailableProduct> unavailableProducts) {
        return !unavailableProducts.isEmpty();
    }

    public OngoingOrder getOngoingOrder(String[] productIds) {
        try {
            List<Product> products = Arrays.stream(productIds)
                    .map(productService::loadProductById)
                    .toList();

            List<ProductDetails> productsInCart =
                    productDetailsService.generateProductDetails(products);

            double totalPrice =
                    productDetailsService.getTotalPrice(productsInCart);

            return new OngoingOrder(productsInCart, totalPrice);

        } catch (Exception exception) {
            throw new CustomRuntimeException(HttpStatus.BAD_REQUEST, "Could not retrieve ongoing order");
        }
    }

    public List<PlacedOrder> getUsersOrders(String userId) {
        List<Order> orders = findOrdersByUserId(userId);

        return orders.stream()
                .map(PlacedOrder::new)
                .toList();
    }

    public List<Order> findOrdersByUserId(String userId) {
        return orderRepository.findOrdersByUserId(userId).orElseThrow(
                () -> new CustomRuntimeException(
                        HttpStatus.NOT_FOUND,
                        "Could not find orders placed by user with id: " + userId));
    }

}

