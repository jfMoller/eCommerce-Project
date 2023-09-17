package com.ecommerce.service.user;

import com.ecommerce.auth.JwtTokenProvider;
import com.ecommerce.controller.login.LoginCredentials;
import com.ecommerce.entity.User;
import com.ecommerce.entity.UserDetails;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserDetailsService userDetailsService;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserService(UserRepository userRepository, UserDetailsService userDetailsService, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public ResponseEntity<Object> submitLogin(LoginCredentials loginCredentials) {
        Map<String, Object> response = new HashMap<>();

        if (userDetailsService.findUser(loginCredentials) != null) {
            String _id = userDetailsService.findUserId(loginCredentials);
            UserDetails details = userDetailsService.getUserDetails(_id);
            String jwtToken = jwtTokenProvider.generateToken(_id, details.getEmail(), details.getRole().toString());

            response.put("status", LoginStatus.SUCCESS);
            response.put("userRole", details.getRole());
            response.put("token", jwtToken);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        response.put("status", LoginStatus.ERROR);
        response.put("message", "Invalid credentials");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

}

enum LoginStatus {
    SUCCESS,

    ERROR;

    @Override
    public String toString() {
        return this.name();
    }
}