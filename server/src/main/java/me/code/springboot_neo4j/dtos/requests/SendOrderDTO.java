package me.code.springboot_neo4j.dtos.requests;

public record SendOrderDTO(String orderId, String expectedDeliveryDate) {
}