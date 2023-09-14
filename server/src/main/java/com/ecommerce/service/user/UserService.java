package com.ecommerce.service.user;

import com.ecommerce.entity.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.CollectionService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CollectionService collectionService;

    @Autowired
    public UserService(UserRepository userRepository, CollectionService collectionService) {
        this.userRepository = userRepository;
        this.collectionService = collectionService;
    }

    @PostConstruct
    public void setupCollection() {
        collectionService.setupCollection("users");
    }

    public ResponseEntity<List<User>> getOrders() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

}