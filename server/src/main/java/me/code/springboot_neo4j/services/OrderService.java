package me.code.springboot_neo4j.services;

import me.code.springboot_neo4j.dto.response.success.Success;
import me.code.springboot_neo4j.dto.response.success.variant.OngoingOrderSuccess;
import me.code.springboot_neo4j.exceptions.types.UncheckedException;
import me.code.springboot_neo4j.models.Order;
import me.code.springboot_neo4j.models.Product;
import me.code.springboot_neo4j.models.User;
import me.code.springboot_neo4j.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserAccountService userAccountService;
    private final ProductService productService;

    @Autowired
    public OrderService(
            OrderRepository orderRepository,
            UserAccountService userAccountService,
            ProductService productService) {
        this.orderRepository = orderRepository;
        this.userAccountService = userAccountService;
        this.productService = productService;
    }

    public Success placeOrder(String userId, String[] productIds) {
        try {
            User user = userAccountService.loadUserById(userId);
            List<Product> orderedProducts = new ArrayList<>();

            for (String productId : productIds) {
                orderedProducts.add(productService.loadProductById(productId));
            }
            orderRepository.save(new Order(user, orderedProducts));
            return new Success(HttpStatus.OK, "The order was placed successfully");

        } catch (Exception exception) {
            throw new UncheckedException(HttpStatus.BAD_REQUEST, "Could not place order");
        }
    }

    public OngoingOrderSuccess getOngoingOrder(String[] productIds) {
        try {
            List<Product> products = productService.getProductsById(productIds);
            double totalPrice = productService.calculateTotalPrice(products);

            return new OngoingOrderSuccess(
                    HttpStatus.OK,
                    "Ongoing order retrieved successfully",
                    products, totalPrice);
        } catch (Exception exception) {
            throw new UncheckedException(HttpStatus.BAD_REQUEST, "Could not retrieve ongoing order");
        }
    }

    public List<Order> getUsersOrders(String userId) {
        return findOrdersByUserId(userId);
    }

    public List<Order> findOrdersByUserId(String userId) {
        return orderRepository.findOrdersWithProductsByUserId(userId).orElseThrow(
                () -> new UncheckedException(
                        HttpStatus.NOT_FOUND,
                        "Could not find orders placed by user with id: " + userId));
    }

    public List<Product> findProductsByOrderId(String orderId) {
        return orderRepository.findProductsByOrderId(orderId).orElseThrow(
                () -> new UncheckedException(
                        HttpStatus.NOT_FOUND,
                        "Could not find product in order with id: " + orderId));
    }
}

