package me.code.springboot_neo4j.dto.request;

public record InsertProductDTO(String name, String description, String imageUrl, double price, int quantity) {
    }