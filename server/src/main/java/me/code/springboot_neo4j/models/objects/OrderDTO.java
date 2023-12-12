package me.code.springboot_neo4j.models.objects;

import lombok.Getter;
import lombok.Setter;
import me.code.springboot_neo4j.models.Order;
import me.code.springboot_neo4j.models.ProductDetail;

import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private Order order;
    private List<ProductDetail> details;

    public OrderDTO(Order order, List<ProductDetail> details) {
        this.order = order;
        this.details = details;
    }
}

