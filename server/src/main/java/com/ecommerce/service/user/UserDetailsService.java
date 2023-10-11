package com.ecommerce.service.user;

import com.ecommerce.auth.JwtTokenProvider;
import com.ecommerce.controller.account.LoginCredentials;
import com.ecommerce.entity.User;
import com.ecommerce.entity.UserDetails;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsService {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserDetailsService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public User findUser(LoginCredentials loginCredentials) {
        return userRepository.findAll().stream()
                .filter(user -> user.getEmail().equals(loginCredentials.email()) &&
                        user.getPassword().equals(loginCredentials.password()))
                .findFirst()
                .orElse(null);
    }

    public User findUser(String user_id) {
        Optional<User> requestedUser = userRepository.findById(user_id);
        return requestedUser.orElse(null);
    }

    public User findUserByToken(String token) {
        String user_id = jwtTokenProvider.getToken_id(token);
        return findUser(user_id);
    }

    public boolean isExistingUsername(String username) {
        return userRepository.findAll().stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }

    public boolean isExistingEmail(String email) {
        return userRepository.findAll().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    public boolean isUsersCurrentEmail(String user_id, String email) {
        User requestedUser = findUser(user_id);
        return requestedUser.getEmail().equals(email);
    }

    public boolean isUsersCurrentPassword(String user_id, String password) {
        User requestedUser = findUser(user_id);
        return requestedUser.getPassword().equals(password);
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
