package me.code.springboot_neo4j.dto.response.error.detailvariant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.code.springboot_neo4j.dto.response.error.ErrorDetail;

import java.util.List;

@Getter
public class OrderErrorDetail extends ErrorDetail {

    @AllArgsConstructor
    public static class ProductError {
        @JsonProperty("errorMessage")
        private String errorMessage;

        @JsonProperty("productId")
        private String productId;

        @JsonProperty("availableAmount")
        private int availableAmount;

        @JsonProperty("requestedAmount")
        private int requestedAmount;
    }

    private List<ProductError> unavailableProducts;

    public OrderErrorDetail(String message, List<ProductError> unavailableProducts) {
        super(message);
        this.unavailableProducts = unavailableProducts;
    }

}
