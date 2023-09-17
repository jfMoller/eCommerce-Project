package com.ecommerce.controller.login;


import com.ecommerce.auth.JwtTokenProvider;
import com.ecommerce.service.user.UserDetailsService;
import com.ecommerce.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final UserService userService;
    private final UserDetailsService userDetailsService;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public LoginController(
            UserService userService, UserDetailsService userDetailsService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginCredentials loginCredentials) {
        return userService.submitLogin(loginCredentials);
    }
}
