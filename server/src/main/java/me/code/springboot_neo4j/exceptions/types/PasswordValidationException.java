package me.code.springboot_neo4j.exceptions.types;

import lombok.Getter;
import me.code.springboot_neo4j.dto.response.error.variant.ValidationErrorDetail;

@Getter
public class PasswordValidationException extends ValidationException {

    public PasswordValidationException(String message, ValidationErrorDetail validationError) {
        super(message, validationError);
    }
}
