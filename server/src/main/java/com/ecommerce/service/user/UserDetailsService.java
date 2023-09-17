package com.ecommerce.service.user;

import com.ecommerce.controller.login.LoginCredentials;
import com.ecommerce.entity.User;
import com.ecommerce.entity.UserDetails;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUser(LoginCredentials loginCredentials) {
        return userRepository.findAll().stream()
                .filter(user -> user.getEmail().equals(loginCredentials.email()) &&
                        user.getPassword().equals(loginCredentials.password()))
                .findFirst()
                .orElse(null);
    }

    public String findUserId(LoginCredentials loginCredentials) {
        return userRepository.findAll().stream()
                .filter(user -> user.getEmail().equals(loginCredentials.email()) &&
                        user.getPassword().equals(loginCredentials.password()))
                .map(User::get_id)
                .findFirst()
                .orElse(null);
    }

    public UserDetails getUserDetails(String user_id) {
        Optional<User> requestedUser = userRepository.findById(user_id);
        if (requestedUser.isPresent()) {
            User user = requestedUser.get();
            return new UserDetails(user.getEmail(), user.getUsername(), user.getPassword(), user.getRole());
        }
        return null;
    }

}
