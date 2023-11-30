package me.code.springboot_neo4j.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Node("Order")
public class Order {
    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    String id;
    @Relationship("PLACED_BY")
    User user;
    @Relationship("ORDERED")
    List<Product> products;
    private double price;
    private OrderStatus status;
    private LocalDateTime received;
    private LocalDateTime expectedDelivery;

    public Order(User user, List<Product> products) {
        this.user = user;
        this.products = products;
        this.price = calculateTotalPrice();
        this.status = OrderStatus.PENDING;
        this.received = getTimeReceived();
        this.expectedDelivery = null;
    }

    private double formatPrice(double price) {
        return Math.round(price * 100.0) / 100.0;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getReceived() {
        return received;
    }

    public LocalDateTime getExpectedDelivery() {
        return expectedDelivery;
    }

    private double calculateTotalPrice() {
        double price = 0;
        for (Product product : products) {
            price += product.getPrice();
        }
        return formatPrice(price);
    }

    private LocalDateTime getTimeReceived() {
        return LocalDateTime.now();
    }

    enum OrderStatus {
        PENDING, PROCESSING, SHIPPED, DELIVERED
    }
}
