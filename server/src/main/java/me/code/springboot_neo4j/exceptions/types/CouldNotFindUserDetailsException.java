package me.code.springboot_neo4j.exceptions.types;

public class CouldNotFindUserDetailsException extends RuntimeException {

    public CouldNotFindUserDetailsException(String message) {
        super(message);
    }
}
