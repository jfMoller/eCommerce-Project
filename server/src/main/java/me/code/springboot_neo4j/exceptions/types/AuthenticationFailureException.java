package me.code.springboot_neo4j.exceptions.types;

/**
 * Exception class representing a failure during user authentication.
 */
public class AuthenticationFailureException extends RuntimeException {

    /**
     * Constructs an AuthenticationFailureException with the specified details message.
     *
     * @param message the details message.
     */
    public AuthenticationFailureException(String message) {
        super(message);
    }
}
