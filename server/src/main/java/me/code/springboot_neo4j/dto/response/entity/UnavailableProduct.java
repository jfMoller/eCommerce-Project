package me.code.springboot_neo4j.dto.response.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UnavailableProduct {
    @JsonProperty("message")
    private String message;

    @JsonProperty("productId")
    private String productId;

    @JsonProperty("requestedAmount")
    private int requestedAmount;

    @JsonProperty("availableAmount")
    private int availableAmount;
}