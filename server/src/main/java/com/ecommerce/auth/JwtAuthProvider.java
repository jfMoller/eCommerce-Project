package com.ecommerce.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class JwtAuthProvider {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtAuthProvider(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public <T> T authorizeAccess(String token, Supplier<T> method) {
        if (jwtTokenProvider.isValidToken(token)) {
            return method.get();
        } else {
            throw new InvalidTokenException("Invalid token; access denied.");
        }
    }

    public <T> T authorizeAccess(boolean requiresAdminRole, String token, Supplier<T> method) {
        if (jwtTokenProvider.hasAdminRole(token)) {
            return method.get();
        } else {
            throw new InvalidAdminTokenException("Invalid admin token; access denied.");
        }
    }

}

class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}

class InvalidAdminTokenException extends RuntimeException {
    public InvalidAdminTokenException(String message) {
        super(message);
    }
}
