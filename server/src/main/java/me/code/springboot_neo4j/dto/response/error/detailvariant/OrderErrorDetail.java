package me.code.springboot_neo4j.dto.response.error.detailvariant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.code.springboot_neo4j.dto.response.error.ErrorDetail;

import java.util.List;

@Getter
public class OrderErrorDetail extends ErrorDetail {

    @AllArgsConstructor
    public static class UnavailableProduct {
        @JsonProperty("errorMessage")
        private String errorMessage;

        @JsonProperty("productId")
        private String productId;

        @JsonProperty("requestedAmount")
        private int requestedAmount;

        @JsonProperty("availableAmount")
        private int availableAmount;
    }

    private List<UnavailableProduct> unavailableProducts;

    public OrderErrorDetail(String message, List<UnavailableProduct> unavailableProducts) {
        super(message);
        this.unavailableProducts = unavailableProducts;
    }

}
