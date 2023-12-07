package me.code.springboot_neo4j.exceptions.types.variant;

import lombok.Getter;
import me.code.springboot_neo4j.dto.response.error.detailvariant.ValidationErrorDetail;
import me.code.springboot_neo4j.exceptions.types.UncheckedException;
import org.springframework.http.HttpStatus;

@Getter
public class ValidationException extends UncheckedException {

    private final ValidationErrorDetail validationError;

    public ValidationException(HttpStatus status, String message, ValidationErrorDetail validationError) {
        super(status, message);
        this.validationError = validationError;
    }
}
