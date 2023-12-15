package me.code.springboot_neo4j.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import me.code.springboot_neo4j.models.nodes.User;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.security.Key;
import java.util.Map;

@Component
@NoArgsConstructor
public class JwtTokenUtil {

    private static final String CONFIG_FILE = "secrets-config.yml";
    private static final String JWT_SECRET = "jwt-secret";

    private final String secret = getJwtSecret();
    private final Key key = Keys.hmacShaKeyFor(secret.getBytes());

    public String generateToken(User user) {
        String id = user.getId();
        String username = user.getUsername();
        String role = user.getRole().toString();

        return Jwts.builder()
                .setSubject(id)
                .claim("id", id)
                .claim("username", username)
                .claim("role", role)
                .signWith(key)
                .compact();
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException jwtException) {
            return false;
        }
    }

    public String getTokenUsername(String token) {
        return getTokenClaim(token, "username");
    }

    public String getTokenId(String token) {
        return getTokenClaim(token, "id");
    }

    public String getTokenClaim(String token, String type) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get(type, String.class);
    }

    private String getJwtSecret() {
        InputStream inputStream = JwtTokenUtil.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
        Map<String, String> config = new Yaml().load(inputStream);

        return config.get(JWT_SECRET);
    }
}
