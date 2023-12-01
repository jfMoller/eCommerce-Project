package me.code.springboot_neo4j.dto.response.success.variant;

import com.fasterxml.jackson.annotation.JsonProperty;
import me.code.springboot_neo4j.dto.response.success.Success;
import org.springframework.http.HttpStatus;

public class UserDetailsSuccess extends Success {

    @JsonProperty("email")
    private String email;

    @JsonProperty("username")
    private String username;

    public UserDetailsSuccess(HttpStatus status, String message, String email, String username) {
        super(status, message);
        this.email = email;
        this.username = username;
    }
}
