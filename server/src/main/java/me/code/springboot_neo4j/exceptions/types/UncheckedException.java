package me.code.springboot_neo4j.exceptions.types;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UncheckedException extends RuntimeException {
    private final HttpStatus status;

    public UncheckedException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
