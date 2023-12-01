package me.code.springboot_neo4j.dto.request;

public record CreateUserDTO(String username, String email, String password) {
}