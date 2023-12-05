package me.code.springboot_neo4j.exceptions.types.unchecked;

import lombok.Getter;
import me.code.springboot_neo4j.dto.response.error.variant.ValidationErrorDetail;
import me.code.springboot_neo4j.exceptions.types.unchecked.UncheckedException;
import org.springframework.http.HttpStatus;

@Getter
public class ValidationException extends UncheckedException {

    private final ValidationErrorDetail validationError;

    public ValidationException(HttpStatus status, String message, ValidationErrorDetail validationError) {
        super(status, message);
        this.validationError = validationError;
    }
}
