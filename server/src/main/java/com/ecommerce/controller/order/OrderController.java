package com.ecommerce.controller.order;


import com.ecommerce.auth.JwtAuthProvider;
import com.ecommerce.dto.PlaceOrderRequest;
import com.ecommerce.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    private final JwtAuthProvider jwtAuthProvider;

    @Autowired
    public OrderController(OrderService orderService, JwtAuthProvider jwtAuthProvider) {
        this.orderService = orderService;
        this.jwtAuthProvider = jwtAuthProvider;
    }

    @PostMapping("/place")
    public ResponseEntity<Object> placeOrder(@RequestHeader("Authorization") String token, @RequestBody PlaceOrderRequest request) {
        return jwtAuthProvider.authorizeAccess(token,
                () -> orderService.placeOrder(token, request.product_ids()));
    }

    @GetMapping("/all")
    public List<Object> getUserOrders(@RequestHeader("Authorization") String token) {
        return jwtAuthProvider.authorizeAccess(token,
                () -> orderService.getUserOrders(token));
    }

}
