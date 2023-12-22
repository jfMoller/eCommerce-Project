package me.code.springboot_neo4j.dtos.responses.error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorDetail {

    @JsonProperty("message")
    private String message;

    public ErrorDetail(String message) {
        this.message = message;
    }
}

