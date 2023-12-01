package me.code.springboot_neo4j.controllers;

import me.code.springboot_neo4j.dtos.GetOngoingOrderDTO;
import me.code.springboot_neo4j.dtos.PlaceOrderDTO;
import me.code.springboot_neo4j.models.Order;
import me.code.springboot_neo4j.services.OrderService;
import me.code.springboot_neo4j.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrderController {
    private final OrderService orderService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public OrderController(OrderService orderService, JwtTokenUtil jwtTokenUtil) {
        this.orderService = orderService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/ongoing")
    public Object getOngoingOrder(@RequestBody GetOngoingOrderDTO dto) {
        return orderService.getOngoingOrder(dto.productIds());
    }

    @PostMapping("/place")
    public ResponseEntity<Object> placeOrder(
            @RequestHeader("Authorization") String token,
            @RequestBody PlaceOrderDTO dto) {
        String userId = jwtTokenUtil.getTokenId(token);
        var result = orderService.placeOrder(userId, dto.productIds());
        return result;
    }

    @GetMapping("/all")
    public List<Order> getUserOrders(@RequestHeader("Authorization") String token) {
        var userId = jwtTokenUtil.getTokenId(token);
        return orderService.getUsersOrders(userId);
    }
}
