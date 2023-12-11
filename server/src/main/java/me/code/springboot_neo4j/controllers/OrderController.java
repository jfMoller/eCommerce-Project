package me.code.springboot_neo4j.controllers;

import me.code.springboot_neo4j.dto.request.GetOngoingOrderDTO;
import me.code.springboot_neo4j.dto.request.PlaceOrderDTO;
import me.code.springboot_neo4j.dto.response.success.Success;
import me.code.springboot_neo4j.dto.response.entity.OngoingOrder;
import me.code.springboot_neo4j.dto.response.entity.PlacedOrder;
import me.code.springboot_neo4j.security.JwtTokenUtil;
import me.code.springboot_neo4j.services.OrderService;
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
    public OngoingOrder getOngoingOrder(@RequestBody GetOngoingOrderDTO dto) {
        return orderService.getOngoingOrder(dto.productIds());
    }

    @PostMapping("/place")
    public ResponseEntity<Success> placeOrder(@RequestHeader("Authorization") String token, @RequestBody PlaceOrderDTO dto) {
        String userId = jwtTokenUtil.getTokenId(token);
        var result = orderService.placeOrder(userId, dto.productIds());
        return result.toResponseEntity();
    }

    @GetMapping("/all")
    public ResponseEntity<List<PlacedOrder>> getUserOrders(@RequestHeader("Authorization") String token) {
        var userId = jwtTokenUtil.getTokenId(token);
        List<PlacedOrder> result = orderService.getUsersOrders(userId);
        return ResponseEntity.ok(result);
    }
}
