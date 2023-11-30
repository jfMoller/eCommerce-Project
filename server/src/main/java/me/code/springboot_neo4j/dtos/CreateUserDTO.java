package me.code.springboot_neo4j.dtos;

public record CreateUserDTO(String username, String email, String password) {
}