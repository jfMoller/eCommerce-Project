package me.code.springboot_neo4j.exceptions.types;

/**
 * Exception class representing a scenario where a requested folder could not be found.
 */
public class CouldNotFindFolderException extends RuntimeException {

    /**
     * Constructs a CouldNotFindFolderException with the specified details message.
     *
     * @param message the details message.
     */
    public CouldNotFindFolderException(String message) {
        super(message);
    }
}
