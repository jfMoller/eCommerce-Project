package me.code.springboot_neo4j.exceptions.types;

/**
 * Exception class representing a scenario where a requested user could not be found.
 */
public class CouldNotFindUserException extends RuntimeException {

    /**
     * Constructs a CouldNotFindUserException with the specified details message.
     *
     * @param message the details message.
     */
    public CouldNotFindUserException(String message) {
        super(message);
    }
}
