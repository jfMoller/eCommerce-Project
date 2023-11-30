package me.code.springboot_neo4j.services;

import me.code.springboot_neo4j.models.User;
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

    public boolean isValidUserCredentials(String email, String password) {
        return userRepository.isValidCredentials(email, password);
    }

    public boolean isExistingEmail(String email) {
        return userRepository.isExistingEmail(email);
    }

    public boolean isExistingUsername(String username) {
        return userRepository.isExistingUsername(username);
    }

    public boolean isUsersCurrentEmail(String userId, String email) {
        return userRepository.isUsersCurrentEmail(userId, email);
    }

    public boolean isUsersCurrentPassword(String userId, String password) {
        return userRepository.isUsersCurrentPassword(userId, password);
    }

    public User loadUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User with id: " + userId + " not found"));
    }

    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User with email: " + email + " not found"));
    }

    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("User with username: " + username + " not found"));
    }
}
