package me.code.springboot_neo4j.models.nodes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Node("Order")
public class Order {

    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;
    private double price;
    private Status status;
    private LocalDateTime received;
    private LocalDateTime expectedDelivery;

    @Relationship(type = "PLACED_BY")
    User user;

    @Relationship(type = "INCLUDES")
    private List<OrderItem> items;

    public Order(User user, List<OrderItem> items) {
        this.user = user;
        this.items = items;
        this.price = getTotalPrice();
        this.status = Status.PENDING;
        this.received = LocalDateTime.now();
        this.expectedDelivery = null;
    }

    public double getTotalPrice() {
        return formatPrice(items.stream()
                .mapToDouble(OrderItem::getPrice)
                .sum());
    }

    private double formatPrice(double price) {
        return Math.round(price * 100.0) / 100.0;
    }

    public enum Status {
        PENDING, PROCESSING, SHIPPED, DELIVERED
    }
}
