package me.code.springboot_neo4j.dtos.requests;

public record AddProductDTO(
        String name,
        String description,
        String imageUrl,
        double price,
        int quantity) {
}