package com.ecommerce.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "orders")
public class Order {

    @Id
    public String _id;

    private String user_id;
    private List<Product> products;
    private double price;

    private OrderStatus status;

    private LocalDateTime received;

    private LocalDateTime expectedDelivery;

    public Order(String user_id, List<Product> products) {
        this.user_id = user_id;
        this.products = products;
        this.price = calculateTotalPrice();
        this.status = OrderStatus.PENDING;
        this.received = getTimeReceived();
        this.expectedDelivery = null; // Has to be set by Admin
    }

    public String get_id() {
        return _id;
    }

    public String getUser_id() {
        return user_id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getPrice() {
        return formatPrice(price);
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
}

enum OrderStatus {
    PENDING, PROCESSING, SHIPPED, DELIVERED
}