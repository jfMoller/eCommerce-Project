package me.code.springboot_neo4j.models;

public enum UserRole {
    REGULAR_USER,
    ADMIN;

    @Override
    public String toString() {
        return this.name();
    }
}
