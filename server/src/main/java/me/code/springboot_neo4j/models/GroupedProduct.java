package me.code.springboot_neo4j.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

@Getter
@Setter
@AllArgsConstructor
public class GroupedProduct {

    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    String id;

    @Relationship("REFERS_TO")
    private Product product;

    private int amount;
    private double groupPrice;

    public GroupedProduct(Product product, int amount) {
        this.product = product;
        this.amount = amount;
        this.groupPrice = product.getPrice() * amount;
    }
}