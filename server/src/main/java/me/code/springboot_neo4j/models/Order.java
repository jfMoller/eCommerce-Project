package me.code.springboot_neo4j.models;

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
    @Id @GeneratedValue(UUIDStringGenerator.class)
    String id;

    @Relationship(type = "PLACED_BY")
    User user;

    @Relationship(type = "INCLUDES")
    private List<ProductDetail> details;

    private double price;
    private OrderStatus status;
    private LocalDateTime received;
    private LocalDateTime expectedDelivery;

    public Order(User user, List<ProductDetail> details) {
        this.user = user;
        this.details = details;
        this.price = getTotalPrice();
        this.status = OrderStatus.PENDING;
        this.received = getTimeReceived();
        this.expectedDelivery = null;
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

    private LocalDateTime getTimeReceived() {
        return LocalDateTime.now();
    }

    public enum OrderStatus {
        PENDING, PROCESSING, SHIPPED, DELIVERED
    }

    public double getTotalPrice() {
        double sum = 0;

        for (var detail : details) {
            sum += detail.getGroupPrice();
        }

        return formatPrice(sum);
    }

    private double formatPrice(double price) {
        return Math.round(price * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", details=" + details +
                ", price=" + price +
                ", status=" + status +
                ", received=" + received +
                ", expectedDelivery=" + expectedDelivery +
                '}';
    }
}
