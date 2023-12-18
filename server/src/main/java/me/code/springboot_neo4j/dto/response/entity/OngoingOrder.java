package me.code.springboot_neo4j.dto.response.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import me.code.springboot_neo4j.models.nodes.ProductDetails;

import java.util.List;

public class OngoingOrder {

    @JsonProperty("products")
    private List<ProductDetails> productDetails;

    @JsonProperty("totalPrice")
    private double totalPrice;

    public OngoingOrder(List<ProductDetails> productDetails, double totalPrice) {
        this.productDetails = productDetails;
        this.totalPrice = totalPrice;
    }
}
