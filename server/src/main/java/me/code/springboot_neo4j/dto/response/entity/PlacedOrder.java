package me.code.springboot_neo4j.dto.response.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import me.code.springboot_neo4j.models.GroupedProduct;
import me.code.springboot_neo4j.models.Order;

import java.time.LocalDateTime;
import java.util.List;

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

    @JsonProperty("products")
    private List<GroupedProduct> groupedProducts;

    public PlacedOrder(Order order) {
        this.id = order.getId();
        this.price = order.getPrice();
        this.status = order.getStatus();
        this.received = order.getReceived();
        this.expectedDelivery = order.getExpectedDelivery();
        this.groupedProducts = order.getProducts();
    }

}
