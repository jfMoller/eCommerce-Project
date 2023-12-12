package me.code.springboot_neo4j.models;

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
@Node("ProductDetail")
public class ProductDetail {

    @Id @GeneratedValue(UUIDStringGenerator.class)
    String id;

    @Relationship(value = "REFERS_TO", direction = Relationship.Direction.OUTGOING)
    private Product product;

    private int amount;
    private double groupPrice;

    public ProductDetail(Product product, int amount) {
        this.product = product;
        this.amount = amount;
        this.groupPrice = product.getPrice() * amount;
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "id='" + id + '\'' +
                ", product=" + product +
                ", amount=" + amount +
                ", groupPrice=" + groupPrice +
                '}';
    }
}