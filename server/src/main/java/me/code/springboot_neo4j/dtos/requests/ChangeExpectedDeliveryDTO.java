package me.code.springboot_neo4j.dtos.requests;

public record ChangeExpectedDeliveryDTO(String orderId, String newExpectedDelivery) {
}