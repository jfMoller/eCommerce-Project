package com.ecommerce.service.order;

import com.ecommerce.dto.JsonResponseProvider;
import com.ecommerce.dto.ResponseStatus;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.product.ProductService;
import com.ecommerce.service.user.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final ProductService productService;

    private final UserDetailsService userDetailsService;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        ProductService productService,
                        UserDetailsService userDetailsService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productService = productService;
        this.userDetailsService = userDetailsService;
    }

    public ResponseEntity<Object> placeOrder(String token, String[] product_ids) {

        if (product_ids.length < 1) {
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatus.ERROR,
                    HttpStatus.BAD_REQUEST,
                    "No product_ids were requested.");
        }
        User orderingUser = userDetailsService.findUserByToken(token);

        if (orderingUser != null) {
            List<Product> orderedProducts = new ArrayList<>();
            for (String product_id : product_ids) {
                orderedProducts.add(productService.getProductById(product_id));
            }
            Order newOrder = new Order(orderingUser.get_id(), orderedProducts);
            orderRepository.save(newOrder);

            orderingUser.addOrder(newOrder.get_id());
            userRepository.save(orderingUser);

            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatus.SUCCESS,
                    HttpStatus.OK,
                    "The order was placed successfully");
        }
        return JsonResponseProvider.sendResponseEntity(
                ResponseStatus.ERROR,
                HttpStatus.BAD_REQUEST,
                "The user placing this order could not be found.");
    }


    public ResponseEntity<List<Order>> getUserOrders(String token) {
        User user = userDetailsService.findUserByToken(token);
        List<Order> orders = new ArrayList<>();

        if (user != null) {
            List<String> order_ids = user.getOrder_ids();

            for (String order_id : order_ids) {
                Optional<Order> order = orderRepository.findById(order_id);
                order.ifPresent(orders::add);
            }
        }
        return ResponseEntity.ok(orders);
    }

}