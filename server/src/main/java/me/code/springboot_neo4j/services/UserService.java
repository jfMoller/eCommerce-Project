package me.code.springboot_neo4j.services;

import me.code.springboot_neo4j.dtos.responses.entities.UserOrderDTO;
import me.code.springboot_neo4j.exceptions.types.CustomRuntimeException;
import me.code.springboot_neo4j.models.nodes.Order;
import me.code.springboot_neo4j.models.nodes.User;
import me.code.springboot_neo4j.repositories.OrderRepository;
import me.code.springboot_neo4j.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public UserService(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(User newUser) {
        return userRepository.save(newUser);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public List<UserOrderDTO> getAllUsersOrders() {
        return findAllUsersOrders().stream()
                .map(order -> new UserOrderDTO(order, order.getUser().getEmail()))
                .toList();
    }

    public List<Order> findAllUsersOrders() {
        return orderRepository.findAllUsersOrders().orElseThrow(
                () -> new CustomRuntimeException(
                        HttpStatus.NOT_FOUND,
                        "Could not find all user's orders"));
    }
}
