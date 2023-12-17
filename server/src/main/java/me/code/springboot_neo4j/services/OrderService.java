package me.code.springboot_neo4j.services;

import jakarta.transaction.Transactional;
import me.code.springboot_neo4j.dto.response.entity.OngoingOrder;
import me.code.springboot_neo4j.dto.response.entity.PlacedOrder;
import me.code.springboot_neo4j.dto.response.error.detailvariant.OrderErrorDetail;
import me.code.springboot_neo4j.dto.response.success.Success;
import me.code.springboot_neo4j.exceptions.types.CustomRuntimeException;
import me.code.springboot_neo4j.exceptions.types.variant.OrderException;
import me.code.springboot_neo4j.models.nodes.Order;
import me.code.springboot_neo4j.models.nodes.Product;
import me.code.springboot_neo4j.models.nodes.ProductDetail;
import me.code.springboot_neo4j.models.nodes.User;
import me.code.springboot_neo4j.repositories.OrderRepository;
import me.code.springboot_neo4j.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    @Autowired
    public OrderService(
            OrderRepository orderRepository,
            ProductRepository productRepository,
            ProductService productService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @Transactional
    public Success placeOrder(User user, String[] productIds) {
        try {
            List<Product> orderedProducts = Arrays.stream(productIds)
                    .map(productService::loadProductById)
                    .toList();

            List<ProductDetail> productDetails = ProductDetail.generateProductDetails(orderedProducts);
            List<OrderErrorDetail.ProductError> unavailableProducts = checkProductAvailability(productDetails);
            boolean isContainingUnavailableProducts = unavailableProducts != null;

            if (isContainingUnavailableProducts) {
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
            throw new OrderException(
                    exception.getStatus(),
                    exception.getMessage(),
                    exception.getOrderErrorDetail());
        } catch (Exception exception) {
            throw new CustomRuntimeException(HttpStatus.BAD_REQUEST, "Could not place order");
        }
    }

    private List<OrderErrorDetail.ProductError> checkProductAvailability(List<ProductDetail> productDetails) {
        List<OrderErrorDetail.ProductError> unavailableProducts = new ArrayList<>();

        productDetails.forEach(detail -> {
            Product targetProduct = detail.getProduct();
            int requestedAmount = detail.getAmount();
            int availableAmount = targetProduct.getQuantity();

            if ((availableAmount - requestedAmount) >= 0) {
                targetProduct.setQuantity(availableAmount - requestedAmount);
                productRepository.save(targetProduct);
            } else {
                unavailableProducts.add(
                        new OrderErrorDetail.ProductError(
                                "Requested amount not available",
                                targetProduct.getId(),
                                availableAmount,
                                requestedAmount));
            }
        });

        return unavailableProducts.size() > 0 ? unavailableProducts : null;
    }

    public OngoingOrder getOngoingOrder(String[] productIds) {
        try {
            List<Product> products = productService.getProductsById(productIds);
            double totalPrice = productService.calculateTotalPrice(products);

            return new OngoingOrder(products, totalPrice);

        } catch (Exception exception) {
            throw new CustomRuntimeException(HttpStatus.BAD_REQUEST, "Could not retrieve ongoing entity");
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

