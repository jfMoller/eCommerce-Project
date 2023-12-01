package me.code.springboot_neo4j.exceptions.types;

import lombok.Getter;
import me.code.springboot_neo4j.dto.response.error.variant.ValidationErrorDetail;

@Getter
public class NonUniqueValueException extends ValidationException {

    public NonUniqueValueException(String message, ValidationErrorDetail validationError) {
        super(message, validationError);
    }
}
