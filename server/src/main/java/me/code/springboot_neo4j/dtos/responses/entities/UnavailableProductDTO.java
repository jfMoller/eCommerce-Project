package me.code.springboot_neo4j.dtos.responses.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UnavailableProductDTO {

    @JsonProperty("message")
    private String message;

    @JsonProperty("productId")
    private String productId;

    @JsonProperty("requestedAmount")
    private int requestedAmount;

    @JsonProperty("availableAmount")
    private int availableAmount;
}