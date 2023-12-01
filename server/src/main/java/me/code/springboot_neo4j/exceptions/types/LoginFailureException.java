package me.code.springboot_neo4j.exceptions.types;

public class LoginFailureException extends RuntimeException {

    public LoginFailureException(String message) {
        super(message);
    }
}
