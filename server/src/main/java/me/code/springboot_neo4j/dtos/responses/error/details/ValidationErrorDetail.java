package me.code.springboot_neo4j.dtos.responses.error.details;

import lombok.Getter;
import me.code.springboot_neo4j.dtos.responses.error.ErrorDetail;

@Getter
public class ValidationErrorDetail extends ErrorDetail {

    private final String targetObject;
    private final String targetField;
    private final String rejectedValue;

    public ValidationErrorDetail(
            String message,
            String targetObject,
            String targetField,
            String rejectedValue) {
        super(message);
        this.targetObject = targetObject;
        this.targetField = targetField;
        this.rejectedValue = rejectedValue;
    }
}
