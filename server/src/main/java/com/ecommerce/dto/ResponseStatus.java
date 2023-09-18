package com.ecommerce.dto;

public enum ResponseStatus {
    SUCCESS,

    ERROR;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}