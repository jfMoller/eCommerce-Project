package me.code.springboot_neo4j.models.nodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Node("ProductDetails")
public class ProductDetails {

    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;
    private int amount;
    private double groupPrice;

    @Relationship(value = "REFERS_TO", direction = Relationship.Direction.OUTGOING)
    private Product product;

    public ProductDetails(Product product, int amount) {
        this.product = product;
        this.amount = amount;
        this.groupPrice = product.getPrice() * amount;
    }

}