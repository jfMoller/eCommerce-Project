package me.code.springboot_neo4j.controllers;

import me.code.springboot_neo4j.dtos.requests.GetOngoingOrderDTO;
import me.code.springboot_neo4j.dtos.requests.PlaceOrderDTO;
import me.code.springboot_neo4j.dtos.responses.entities.OngoingOrderDTO;
import me.code.springboot_neo4j.dtos.responses.entities.PlacedOrderDTO;
import me.code.springboot_neo4j.dtos.responses.success.Success;
import me.code.springboot_neo4j.models.nodes.Order;
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
    public ResponseEntity<OngoingOrderDTO> getOngoingOrder(@RequestBody GetOngoingOrderDTO dto) {
        var result = orderService.getOngoingOrder(dto.productIds());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/place")
    public ResponseEntity<Success> placeOrder(@AuthenticationPrincipal User user, @RequestBody PlaceOrderDTO dto) {
        var result = orderService.placeOrder(
                user, dto.productIds(), dto.latitude(), dto.longitude(), dto.deliveryMethod(), dto.paymentMethod());
        return result.toResponseEntity();
    }

    @GetMapping("/all")
    public ResponseEntity<List<PlacedOrderDTO>> getUserOrders(@AuthenticationPrincipal User user) {
        var result = orderService.getUserOrders(user.getId());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/delivery/methods")
    public ResponseEntity<List<Order.DeliveryMethod>> getAvailableDeliveryMethods() {
        var result = orderService.getAvailableDeliveryMethods();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/payment/methods")
    public ResponseEntity<List<Order.PaymentMethod>> getAvailablePaymentMethods() {
        var result = orderService.getAvailablePaymentMethods();
        return ResponseEntity.ok(result);
    }

}
