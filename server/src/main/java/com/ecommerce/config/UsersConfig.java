package com.ecommerce.config;

import com.ecommerce.entity.User;
import com.ecommerce.repository.UserRepository;

public class UsersConfig {

    private final UserRepository userRepository;

    public UsersConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createDefaultUsers() {
        if (userRepository.count() == 0) {
            createUser(new User("user@user.com", "password"));
        }
    }

    private void createUser(User user) {
        userRepository.save(user);
        System.out.println("UsersConfig created a new user: " + user.getEmail());
    }
}
