package me.code.springboot_neo4j.exceptions.types;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(String message) {
        super(message);
    }
}
