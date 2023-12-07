package me.code.springboot_neo4j.exceptions;

import jakarta.servlet.ServletException;
import me.code.springboot_neo4j.dto.response.error.Error;
import me.code.springboot_neo4j.dto.response.error.ErrorDetail;
import me.code.springboot_neo4j.dto.response.error.detailvariant.ValidationErrorDetail;
import me.code.springboot_neo4j.exceptions.types.UncheckedException;
import me.code.springboot_neo4j.exceptions.types.variant.ValidationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<Error> buildResponseEntity(HttpStatus status, Throwable exception) {
        return new Error(status, exception).toResponseEntity();
    }

    private <T extends ErrorDetail> ResponseEntity<Error> buildResponseEntity(
            HttpStatus status, Throwable exception, T errorDetail) {
        Error error = new Error(status, exception);
        error.addErrorDetail(errorDetail);
        return error.toResponseEntity();
    }

    @ExceptionHandler({Exception.class, IOException.class, ServletException.class})
    public ResponseEntity<Error> handleException(Exception exception) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    @ExceptionHandler({UncheckedException.class})
    public ResponseEntity<Error> handleUncheckedException(UncheckedException exception) {
        HttpStatus status = exception.getStatus();
        ErrorDetail errorDetail = new ErrorDetail(exception.getMessage());
        return buildResponseEntity(status, exception, errorDetail);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<Error> handleValidationException(ValidationException exception) {
        HttpStatus status = exception.getStatus();
        ValidationErrorDetail validationError = exception.getValidationError();
        return buildResponseEntity(status, exception, validationError);
    }
}