package me.code.springboot_neo4j.controllers;

import me.code.springboot_neo4j.dtos.responses.entities.UserOrderDTO;
import me.code.springboot_neo4j.models.nodes.User;
import me.code.springboot_neo4j.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User newUser) {
        var result = userService.addUser(newUser);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/orders/all")
    public ResponseEntity<List<UserOrderDTO>> getAllUsersOrders() {
        var result = userService.getAllUsersOrders();
        return ResponseEntity.ok(result);
    }

}
