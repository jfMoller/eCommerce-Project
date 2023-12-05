package me.code.springboot_neo4j.dto.response.success.variant;

import com.fasterxml.jackson.annotation.JsonProperty;
import me.code.springboot_neo4j.dto.response.success.Success;
import org.springframework.http.HttpStatus;

public class AuthenticationSuccess extends Success {

    @JsonProperty("userRole")
    private String userRole;

    @JsonProperty("jwtToken")
    private String token;

    public AuthenticationSuccess(HttpStatus status, String message, String userRole, String token) {
        super(status, message);
        this.userRole = userRole;
        this.token = token;
    }
}
