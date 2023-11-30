package me.code.springboot_neo4j.dtos;

public enum ResponseStatusDTO {
    SUCCESS,

    ERROR;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}