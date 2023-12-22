package me.code.springboot_neo4j.dtos.responses.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import me.code.springboot_neo4j.models.nodes.ProductDetails;

import java.util.List;

@Setter
public class OngoingOrderDTO {

    @JsonProperty("productDetails")
    private List<ProductDetails> productDetails;

    @JsonProperty("totalPrice")
    private double totalPrice;

    public OngoingOrderDTO(List<ProductDetails> productDetails, double totalPrice) {
        this.productDetails = productDetails;
        this.totalPrice = totalPrice;
    }
}
