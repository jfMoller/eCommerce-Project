package me.code.springboot_neo4j.dtos.responses.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import me.code.springboot_neo4j.models.nodes.OrderItem;

import java.util.List;

@Setter
public class OngoingOrderDTO {

    @JsonProperty("items")
    private List<OrderItem> items;

    @JsonProperty("totalPrice")
    private double totalPrice;

    public OngoingOrderDTO(List<OrderItem> items, double totalPrice) {
        this.items = items;
        this.totalPrice = totalPrice;
    }
}
