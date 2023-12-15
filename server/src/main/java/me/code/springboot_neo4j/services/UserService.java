package me.code.springboot_neo4j.services;

import me.code.springboot_neo4j.models.nodes.User;
import me.code.springboot_neo4j.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
