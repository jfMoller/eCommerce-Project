package me.code.springboot_neo4j.dto.response.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import me.code.springboot_neo4j.models.Order;
import me.code.springboot_neo4j.models.ProductDetails;

import java.time.LocalDateTime;
import java.util.List;

@Setter
public class PlacedOrder {

    @JsonProperty("id")
    String id;

    @JsonProperty("price")
    private double price;

    @JsonProperty("status")
    private Order.OrderStatus status;

    @JsonProperty("received")
    private LocalDateTime received;

    @JsonProperty("expectedDelivery")
    private LocalDateTime expectedDelivery;

    @JsonProperty("productDetails")
    private List<ProductDetails> productDetails;

    public PlacedOrder(Order order) {
        this.id = order.getId();
        this.price = order.getPrice();
        this.status = order.getStatus();
        this.received = order.getReceived();
        this.expectedDelivery = order.getExpectedDelivery();
        this.productDetails = order.getDetails();
    }

}
