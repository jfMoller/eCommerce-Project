package me.code.springboot_neo4j.dtos.requests;

import me.code.springboot_neo4j.models.nodes.Order;

public record PlaceOrderDTO(
        String[] productIds,
        Order.DeliveryMethod deliveryMethod,
        String deliveryAddress,
        Order.PaymentMethod paymentMethod) {
}