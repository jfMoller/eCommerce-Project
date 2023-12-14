package me.code.springboot_neo4j.config.neo4j;

import me.code.springboot_neo4j.dto.request.CreateUserDTO;
import me.code.springboot_neo4j.models.User;
import me.code.springboot_neo4j.models.UserRole;
import me.code.springboot_neo4j.repositories.UserRepository;
import me.code.springboot_neo4j.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsersConfig {

    private final UserRepository userRepository;
    private final UserAccountService userAccountService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersConfig(UserRepository userRepository, UserAccountService userAccountService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userAccountService = userAccountService;
        this.passwordEncoder = passwordEncoder;
    }


    public void createDefaultUsers() {
        if (userRepository.count() == 0) {
            createMockUsers(new ArrayList<>(List.of(
                    new CreateUserDTO("JohnDoe", "user@user.com", "Password"))));

            createMockAdmins(new ArrayList<>(List.of(
                    new CreateUserDTO("JaneDoe", "admin@admin.com", "Password"))));
        }
    }

    private void createMockUsers(List<CreateUserDTO> mockDtos) {
        for (var mockDto : mockDtos) {
            userAccountService.submitRegistration(mockDto);
            System.out.println("UsersConfig created a new user: " + mockDto.email());
        }
    }

    private void createMockAdmins(List<CreateUserDTO> mockDtos) {
        for (var mockDto : mockDtos) {
            String encryptedPassword = passwordEncoder.encode(mockDto.password());
            User admin = new User(mockDto.email(), mockDto.username(), encryptedPassword, UserRole.ADMIN);

            userRepository.save(admin);
            System.out.println("UsersConfig created a new admin user: " + mockDto.email());
        }
    }

}
