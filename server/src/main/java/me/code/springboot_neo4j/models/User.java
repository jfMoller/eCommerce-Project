package me.code.springboot_neo4j.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

@NoArgsConstructor
@Getter
@Setter
@Node("User")
public class User {
    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    String id;
    private String email;
    private String username;
    private String password;
    private UserRole role;

    public User(String email, String username, String password, UserRole role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
