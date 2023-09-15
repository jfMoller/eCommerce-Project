package com.ecommerce.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthProvider {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtAuthProvider(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public boolean isUserMatch(String token) {
        return false;
    }
    public boolean isAuthorized(String token) {
        return jwtTokenProvider.isValidToken(token);
    }




}
