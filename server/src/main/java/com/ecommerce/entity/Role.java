package com.ecommerce.entity;

public enum Role {
    REGULAR_USER,
    ADMIN;
    @Override
    public String toString() {
        return this.name();
    }
}
