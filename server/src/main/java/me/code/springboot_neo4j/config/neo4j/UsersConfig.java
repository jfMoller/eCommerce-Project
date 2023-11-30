package me.code.springboot_neo4j.config.neo4j;

import me.code.springboot_neo4j.models.User;
import me.code.springboot_neo4j.repositories.UserRepository;

import static me.code.springboot_neo4j.models.UserRole.ADMIN;
import static me.code.springboot_neo4j.models.UserRole.REGULAR_USER;

public class UsersConfig {

    private final UserRepository userRepository;

    public UsersConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createDefaultUsers() {
        if (userRepository.count() == 0) {
            createUser(
                    new User("John Doe", "user@user.com", "password",
                            REGULAR_USER));
            createUser(
                    new User("Jane Doe", "admin@admin.com", "password",
                            ADMIN));
        }
    }

    private void createUser(User user) {
        userRepository.save(user);
        System.out.println("UsersConfig created a new user: " + user.toString());
    }
}
