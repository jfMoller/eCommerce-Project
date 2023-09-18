package com.ecommerce.config.database;

import com.ecommerce.entity.User;
import com.ecommerce.repository.UserRepository;

import static com.ecommerce.entity.Role.ADMIN;
import static com.ecommerce.entity.Role.REGULAR_USER;

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
