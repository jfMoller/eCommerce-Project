package me.code.springboot_neo4j.dto.response.success.variant;

import com.fasterxml.jackson.annotation.JsonProperty;
import me.code.springboot_neo4j.dto.response.success.Success;
import me.code.springboot_neo4j.models.Product;
import org.springframework.http.HttpStatus;

import java.util.List;

public class OngoingOrderSuccess extends Success {

    @JsonProperty("products")
    private List<Product> products;

    @JsonProperty("totalPrice")
    private double totalPrice;

    public OngoingOrderSuccess(HttpStatus status, String message, List<Product> products, double totalPrice) {
        super(status, message);
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
