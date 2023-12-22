package me.code.springboot_neo4j.services;

import jakarta.transaction.Transactional;
import me.code.springboot_neo4j.dtos.responses.entities.OngoingOrderDTO;
import me.code.springboot_neo4j.dtos.responses.entities.PlacedOrderDTO;
import me.code.springboot_neo4j.dtos.responses.entities.UnavailableProductDTO;
import me.code.springboot_neo4j.dtos.responses.error.details.OrderErrorDetail;
import me.code.springboot_neo4j.dtos.responses.success.Success;
import me.code.springboot_neo4j.exceptions.types.CustomRuntimeException;
import me.code.springboot_neo4j.exceptions.types.variants.OrderException;
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

            List<UnavailableProductDTO> unavailableProductDTOS =
                    productDetailsService.findUnavailableProducts(productDetails);

            if (hasUnavailableProducts(unavailableProductDTOS)) {
                throw new OrderException(
                        HttpStatus.BAD_REQUEST,
                        "Could not place order",
                        new OrderErrorDetail(
                                "The order contains unavailable products",
                                unavailableProductDTOS));
            }

            productDetailsService.updateProductQuantities(productDetails);

            orderRepository.save(new Order(user, productDetails));

            return new Success(HttpStatus.OK, "The order was placed successfully");

        } catch (OrderException exception) {
            throw new OrderException(exception.getStatus(), exception.getMessage(), exception.getOrderErrorDetail());

        } catch (Exception exception) {
            throw new CustomRuntimeException(HttpStatus.BAD_REQUEST, "Could not place order");
        }
    }

    private boolean hasUnavailableProducts(List<UnavailableProductDTO> unavailableProductDTOS) {
        return !unavailableProductDTOS.isEmpty();
    }

    public OngoingOrderDTO getOngoingOrder(String[] productIds) {
        try {
            List<Product> products = Arrays.stream(productIds)
                    .map(productService::loadProductById)
                    .toList();

            List<ProductDetails> productsInCart =
                    productDetailsService.generateProductDetails(products);

            double totalPrice =
                    productDetailsService.getTotalPrice(productsInCart);

            return new OngoingOrderDTO(productsInCart, totalPrice);

        } catch (Exception exception) {
            throw new CustomRuntimeException(HttpStatus.BAD_REQUEST, "Could not retrieve ongoing order");
        }
    }

    public List<PlacedOrderDTO> getUsersOrders(String userId) {
        List<Order> orders = findOrdersByUserId(userId);

        return orders.stream()
                .map(PlacedOrderDTO::new)
                .toList();
    }

    public List<Order> findOrdersByUserId(String userId) {
        return orderRepository.findOrdersByUserId(userId).orElseThrow(
                () -> new CustomRuntimeException(
                        HttpStatus.NOT_FOUND,
                        "Could not find orders placed by user with id: " + userId));
    }

}

