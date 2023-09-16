package com.ecommerce.auth;

import com.ecommerce.service.user.UserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

import static com.ecommerce.auth.TokenInfo.*;
import static com.ecommerce.entity.Role.ADMIN;

@Service
public class JwtTokenProvider {
    private final String secret = "keyboardcat-FEWFWEJFnjöFEJNNfejfeÖnfjöwefjwefwfe";
    private final Key secretKey = Keys.hmacShaKeyFor(secret.getBytes());

    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String generateToken(String _id, String email, String role) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 3600000); // Token valid for 1 hour

        return Jwts.builder()
                .setSubject(_id)
                .claim(ID.toString(), _id)
                .claim(EMAIL.toString(), email)
                .claim(ROLE.toString(), role)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String extractTokenInfo(String token, TokenInfo type) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return (String) claims.get(type.toString());
    }

    public boolean hasAdminRole(String token) {
        String role = extractTokenInfo(token, ROLE);
        return role.equals(ADMIN.toString());
    }

    private boolean isValidAdminToken(String token) {
        return isValidToken(token) && hasAdminRole(token);
    }

}

enum TokenInfo {
    ID,
    EMAIL,
    ROLE;

    @Override
    public String toString() {
        return this.name();
    }
}
