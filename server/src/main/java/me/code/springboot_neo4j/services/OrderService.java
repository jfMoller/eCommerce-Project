package me.code.springboot_neo4j.services;

import me.code.springboot_neo4j.dto.response.success.ResponseStatusDTO;
import me.code.springboot_neo4j.models.Order;
import me.code.springboot_neo4j.models.Product;
import me.code.springboot_neo4j.models.User;
import me.code.springboot_neo4j.repositories.OrderRepository;
import me.code.springboot_neo4j.utils.JsonResponseProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<Object> placeOrder(String userId, String[] productIds) {

        if (productIds.length < 1) {
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatusDTO.ERROR,
                    HttpStatus.BAD_REQUEST,
                    "No product_ids were requested.");
        }
        User orderingUser = userAccountService.loadUserById(userId);

        if (orderingUser != null) {
            List<Product> orderedProducts = new ArrayList<>();
            for (String product_id : productIds) {
                orderedProducts.add(productService.getProductById(product_id));
            }
            Order newOrder = new Order(orderingUser, orderedProducts);
            orderRepository.save(newOrder);

            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatusDTO.SUCCESS,
                    HttpStatus.OK,
                    "The order was placed successfully");
        }
        return JsonResponseProvider.sendResponseEntity(
                ResponseStatusDTO.ERROR,
                HttpStatus.UNAUTHORIZED,
                "The user placing this order could not be found.");
    }

    public Object getOngoingOrder(String[] product_ids) {
        List<Product> requestedProducts = productService.getProductsById(product_ids);
        double totalPrice = productService.calculateTotalPrice(requestedProducts);

        return JsonResponseProvider.sendOngoingOrderJson(requestedProducts, totalPrice);
    }


    public List<Order> getUsersOrders(String userId) {
        return findOrdersByUserId(userId);
    }

    public List<Order> findOrdersByUserId(String userId) {
        return orderRepository.findOrdersByUserId(userId).orElseThrow(
                () -> new RuntimeException("Can not find orders with user id: " + userId));
    }
}

