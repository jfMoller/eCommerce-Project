package me.code.springboot_neo4j.models.objects;

import lombok.Getter;
import lombok.Setter;
import me.code.springboot_neo4j.models.Order;
import me.code.springboot_neo4j.models.Product;
import me.code.springboot_neo4j.models.ProductDetail;

import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private Order order;
    private List<ProductDetail> details;
    private List<Product> products;

    // Constructors, getters, and setters

    public OrderDTO(Order order, List<ProductDetail> details, List<Product> products) {
        System.out.println("order: " + order);
        this.order = order;
        System.out.println("details: " + details);
        this.details = details;
        System.out.println("products: " + products);
        this.products = products;
    }
}

