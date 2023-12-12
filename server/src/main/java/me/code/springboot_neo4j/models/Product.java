package me.code.springboot_neo4j.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Node("Product")
public class Product {
    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    String id;
    private String name;
    private String description;
    private String imageUrl;
    private double price;
    private int quantity;

    public Product(String name, String description, String imageUrl, double price, int quantity) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
