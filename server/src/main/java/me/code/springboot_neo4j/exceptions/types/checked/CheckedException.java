package me.code.springboot_neo4j.exceptions.types.checked;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CheckedException extends Exception {
    private final HttpStatus status;

    public CheckedException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
