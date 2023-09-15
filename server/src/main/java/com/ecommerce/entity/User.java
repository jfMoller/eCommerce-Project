package com.ecommerce.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class User {
    @Id
    public String _id;

    private String email;

    private String password;

    private List<Order> orders = new ArrayList<>();

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String get_id() {
        return _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "User{" +
                "_id='" + _id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", orders=" + orders +
                '}';
    }
}