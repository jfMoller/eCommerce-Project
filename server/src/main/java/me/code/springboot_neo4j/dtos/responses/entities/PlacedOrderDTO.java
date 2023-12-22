package me.code.springboot_neo4j.dtos.responses.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import me.code.springboot_neo4j.models.nodes.Order;
import me.code.springboot_neo4j.models.nodes.ProductDetails;

import java.time.LocalDateTime;
import java.util.List;

@Setter
public class PlacedOrderDTO {

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

    public PlacedOrderDTO(Order order) {
        this.id = order.getId();
        this.price = order.getPrice();
        this.status = order.getStatus();
        this.received = order.getReceived();
        this.expectedDelivery = order.getExpectedDelivery();
        this.productDetails = order.getDetails();
    }

}
