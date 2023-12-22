package me.code.springboot_neo4j.config.neo4j;

import me.code.springboot_neo4j.dto.request.CreateUserDTO;
import me.code.springboot_neo4j.models.nodes.User;
import me.code.springboot_neo4j.repositories.UserRepository;
import me.code.springboot_neo4j.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MockUsersConfig {

    private final UserRepository userRepository;
    private final UserAccountService userAccountService;
    private final PasswordEncoder passwordEncoder;

    private static final List<CreateUserDTO> MOCK_USER_DTOS = List.of(
            new CreateUserDTO(
                    "JohnDoe",
                    "user@user.com",
                    "Password"));

    private static final List<CreateUserDTO> MOCK_ADMIN_DTOS = List.of(
            new CreateUserDTO(
                    "JaneDoe",
                    "admin@admin.com",
                    "Password"));

    @Autowired
    public MockUsersConfig(UserRepository userRepository, UserAccountService userAccountService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userAccountService = userAccountService;
        this.passwordEncoder = passwordEncoder;
    }


    public void createDefaultUsers() {
        if (isUserRepositoryEmpty()) {
            MOCK_USER_DTOS.forEach(this::createMockUsers);
            MOCK_ADMIN_DTOS.forEach(this::createMockAdmins);
        }
    }

    public boolean isUserRepositoryEmpty() {
        return userRepository.count() == 0;
    }

    private void createMockUsers(CreateUserDTO dto) {
        userAccountService.submitRegistration(dto);
        System.out.println("MockUsersConfig created a new user: " + dto.email());

    }

    private void createMockAdmins(CreateUserDTO dto) {
        String encryptedPassword = passwordEncoder.encode(dto.password());
        User admin = new User(dto.email(), dto.username(), encryptedPassword, User.Role.ADMIN);

        userRepository.save(admin);
        System.out.println("MockUsersConfig created a new admin user: " + dto.email());
    }

}
