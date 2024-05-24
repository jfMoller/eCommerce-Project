package me.code.springboot_neo4j.dtos.requests;

import me.code.springboot_neo4j.models.nodes.Order;

public record PlaceOrderDTO(
        String[] productIds,
        float latitude,
        float longitude,
        Order.DeliveryMethod deliveryMethod,
        Order.PaymentMethod paymentMethod) {
}