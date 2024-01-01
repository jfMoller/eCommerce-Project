package me.code.springboot_neo4j.dtos.responses.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import me.code.springboot_neo4j.models.nodes.Order;

@Setter
public class UserOrderDTO extends PlacedOrderDTO {

    @JsonProperty("userEmail")
    private String userEmail;

    public UserOrderDTO(Order order, String userEmail) {
        super(order);
        this.userEmail = userEmail;
    }

}
