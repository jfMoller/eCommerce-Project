package me.code.springboot_neo4j.dto.response.error.variant;

import lombok.Getter;
import me.code.springboot_neo4j.dto.response.error.ErrorDetail;

@Getter
public class ValidationErrorDetail extends ErrorDetail {

    private String targetObject;
    private String targetField;
    private Object rejectedValue;

    public ValidationErrorDetail(String message, String targetObject, String targetField, Object rejectedValue) {
        super(message);
        this.targetObject = targetObject;
        this.targetField = targetField;
        this.rejectedValue = rejectedValue;
    }
}
