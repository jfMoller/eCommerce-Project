package com.ecommerce.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "orders2")
public class Order {

    @Id
    public String _id;

    private User user;
    private List<Product> products = new ArrayList<>();
    private double price;

    private OrderStatus status;

    private LocalDateTime received;

    private LocalDateTime expectedDelivery;

    public Order(User user, List<Product> products, double price, OrderStatus status, LocalDateTime received, LocalDateTime expectedDelivery) {
        this.user = user;
        this.products = products;
        this.price = price;
        this.status = status;
        this.received = received;
        this.expectedDelivery = expectedDelivery;
    }

    public String get_id() {
        return _id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getReceived() {
        return received;
    }

    public void setReceived(LocalDateTime received) {
        this.received = received;
    }

    public LocalDateTime getExpectedDelivery() {
        return expectedDelivery;
    }

    public void setExpectedDelivery(LocalDateTime expectedDelivery) {
        this.expectedDelivery = expectedDelivery;
    }
}

enum OrderStatus {
    PENDING, PROCESSING, SHIPPED, DELIVERED
}