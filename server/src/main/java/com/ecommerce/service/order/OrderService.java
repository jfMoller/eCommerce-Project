package com.ecommerce.service.order;

import com.ecommerce.entity.Order;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.service.CollectionService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CollectionService collectionService;

    @Autowired
    public OrderService(OrderRepository orderRepository, CollectionService collectionService) {
        this.orderRepository = orderRepository;
        this.collectionService = collectionService;
    }

    @PostConstruct
    public void setupCollection() {
        collectionService.setupCollection("orders");
    }

    public ResponseEntity<List<Order>> getOrders() {
        List<Order> orders = orderRepository.findAll();
        return ResponseEntity.ok(orders);
    }

}