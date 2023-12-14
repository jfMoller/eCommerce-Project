package me.code.springboot_neo4j.exceptions.types;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomRuntimeException extends RuntimeException {
    private final HttpStatus status;

    public CustomRuntimeException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
