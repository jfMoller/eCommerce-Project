package me.code.springboot_neo4j.dto.response.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import me.code.springboot_neo4j.models.nodes.Product;
import me.code.springboot_neo4j.models.nodes.ProductDetail;

import java.util.List;

public class OngoingOrder {

    @JsonProperty("products")
    private List<ProductDetail> products;

    @JsonProperty("totalPrice")
    private double totalPrice;

    public OngoingOrder(List<Product> products, double totalPrice) {
        this.products = ProductDetail.generateProductDetails(products);
        this.totalPrice = totalPrice;
    }
}
