package me.code.springboot_neo4j.dtos;

public record InsertProductDTO(String name, String description, String imageUrl, double price, int quantity) {
    }