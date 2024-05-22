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
    private Status status;
    private double price;
    private PaymentMethod paymentMethod;
    private DeliveryMethod deliveryMethod;
    private String deliveryAddress;
    private LocalDateTime received;
    private LocalDateTime expectedDelivery;

    @Relationship(type = "PLACED_BY")
    User user;

    @Relationship(type = "INCLUDES")
    private List<OrderItem> items;

    public Order(
            User user,
            List<OrderItem> items,
            String deliveryAddress,
            DeliveryMethod deliveryMethod,
            PaymentMethod paymentMethod) {
        this.status = Status.PENDING;
        this.user = user;
        this.items = items;
        this.price = getTotalPrice();
        this.deliveryAddress = deliveryAddress;
        this.deliveryMethod = deliveryMethod;
        this.paymentMethod = paymentMethod;
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
        PENDING, SENT
    }

    public enum DeliveryMethod {
        HOME_DELIVERY
    }

    public enum PaymentMethod {
        PAY_ON_DELIVERY
    }
}
