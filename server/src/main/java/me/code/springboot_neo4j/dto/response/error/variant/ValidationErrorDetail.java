package me.code.springboot_neo4j.dto.response.error.variant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.code.springboot_neo4j.dto.response.error.ErrorDetail;

@Getter
@AllArgsConstructor
public class ValidationErrorDetail extends ErrorDetail {

    private String targetObject;
    private String targetField;
    private Object rejectedValue;
    private String message;

}
