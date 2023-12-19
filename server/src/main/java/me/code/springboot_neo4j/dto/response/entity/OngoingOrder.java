package me.code.springboot_neo4j.dto.response.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import me.code.springboot_neo4j.models.nodes.ProductDetails;

import java.util.List;

@Setter
public class OngoingOrder {

    @JsonProperty("productDetails")
    private List<ProductDetails> productDetails;

    @JsonProperty("totalPrice")
    private double totalPrice;

    public OngoingOrder(List<ProductDetails> productDetails, double totalPrice) {
        this.productDetails = productDetails;
        this.totalPrice = totalPrice;
    }
}
