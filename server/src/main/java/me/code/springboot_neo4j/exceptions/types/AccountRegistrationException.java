package me.code.springboot_neo4j.exceptions.types;

/**
 * Exception class representing an error during the account registration process.
 */
public class AccountRegistrationException extends RuntimeException {

    /**
     * Constructs an AccountRegistrationException with the specified details message.
     *
     * @param message the details message.
     */
    public AccountRegistrationException(String message) {
        super(message);
    }
}
