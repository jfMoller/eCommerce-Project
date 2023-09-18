package com.ecommerce.service.user;

import com.ecommerce.controller.account.LoginCredentials;
import com.ecommerce.controller.account.RegisterCredentials;
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

    public User findUser(RegisterCredentials registerCredentials) {
        return userRepository.findAll().stream()
                .filter(user -> user.getUsername().equals(registerCredentials.username()) &&
                        user.getEmail().equals(registerCredentials.email()) &&
                        user.getPassword().equals(registerCredentials.password()))
                .findFirst()
                .orElse(null);
    }

    public boolean isExistingUsername(String username) {
        return userRepository.findAll().stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }

    public boolean isExistingEmail(String email) {
        return userRepository.findAll().stream()
                .anyMatch(user -> user.getEmail().equals(email));
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
