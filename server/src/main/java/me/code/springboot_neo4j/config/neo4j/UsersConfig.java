package me.code.springboot_neo4j.config.neo4j;

import me.code.springboot_neo4j.models.User;
import me.code.springboot_neo4j.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static me.code.springboot_neo4j.models.UserRole.ADMIN;
import static me.code.springboot_neo4j.models.UserRole.REGULAR_USER;

@Component
public class UsersConfig {

    private final UserRepository userRepository;

    @Autowired
    public UsersConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createDefaultUsers() {
        if (userRepository.count() == 0) {
            createUser(
                    new User("user@user.com", "John Doe", "password",
                            REGULAR_USER));
            createUser(
                    new User("admin@admin.com", "Jane Doe", "password",
                            ADMIN));
        }
    }

    private void createUser(User user) {
        userRepository.save(user);
        System.out.println("UsersConfig created a new user: " + user);
    }
}
