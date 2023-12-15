package me.code.springboot_neo4j.controllers;

import me.code.springboot_neo4j.dto.request.GetOngoingOrderDTO;
import me.code.springboot_neo4j.dto.request.PlaceOrderDTO;
import me.code.springboot_neo4j.dto.response.entity.OngoingOrder;
import me.code.springboot_neo4j.dto.response.entity.PlacedOrder;
import me.code.springboot_neo4j.dto.response.success.Success;
import me.code.springboot_neo4j.models.nodes.User;
import me.code.springboot_neo4j.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/ongoing")
    public OngoingOrder getOngoingOrder(@RequestBody GetOngoingOrderDTO dto) {
        return orderService.getOngoingOrder(dto.productIds());
    }

    @PostMapping("/place")
    public ResponseEntity<Success> placeOrder(@AuthenticationPrincipal User user, @RequestBody PlaceOrderDTO dto) {
        var result = orderService.placeOrder(user, dto.productIds());
        return result.toResponseEntity();
    }

    @GetMapping("/all")
    public ResponseEntity<List<PlacedOrder>> getUserOrders(@AuthenticationPrincipal User user) {
        List<PlacedOrder> result = orderService.getUsersOrders(user.getId());
        return ResponseEntity.ok(result);
    }
}
