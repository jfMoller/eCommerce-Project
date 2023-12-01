package me.code.springboot_neo4j.exceptions.types;

import lombok.Getter;
import me.code.springboot_neo4j.dto.response.error.variant.ValidationErrorDetail;

@Getter
public class ValidationException extends RuntimeException {

    private final ValidationErrorDetail validationError;

    public ValidationException(String message, ValidationErrorDetail validationError) {
        super(message);
        this.validationError = validationError;
    }
}
