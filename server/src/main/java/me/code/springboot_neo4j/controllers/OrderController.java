package me.code.springboot_neo4j.controllers;

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

    record GetOngoingOrderDTO(String[] productIds) {

    }
    @PostMapping("/ongoing")
    public Object getOngoingOrder(@RequestBody GetOngoingOrderDTO dto) {
        return orderService.getOngoingOrder(dto.productIds());
    }

    record PlaceOrderDto(String userId, String[] productIds) {
    }

    @PostMapping("/place")
    public ResponseEntity<Object> placeOrder(@RequestBody PlaceOrderDto dto) {
        var result = orderService.placeOrder(dto.userId(), dto.productIds());
        return result;
    }

    @GetMapping("/all")
    public List<Order> getUserOrders(@RequestHeader("Authorization") String token) {
        var userId = jwtTokenUtil.getTokenId(token);
        return orderService.getUsersOrders(userId);
    }
}
