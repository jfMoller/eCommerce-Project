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
    private String username;
    private String email;
    private String password;
    private UserRole role;

    public User(String username, String email, String password, UserRole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
