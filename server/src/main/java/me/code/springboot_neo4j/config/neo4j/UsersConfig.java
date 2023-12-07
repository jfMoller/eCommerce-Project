package me.code.springboot_neo4j.config.neo4j;

import me.code.springboot_neo4j.dto.request.CreateUserDTO;
import me.code.springboot_neo4j.repositories.UserRepository;
import me.code.springboot_neo4j.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsersConfig {

    private final UserRepository userRepository;
    private final UserAccountService userAccountService;

    @Autowired
    public UsersConfig(UserRepository userRepository, UserAccountService userAccountService) {
        this.userRepository = userRepository;
        this.userAccountService = userAccountService;
    }


    public void createDefaultUsers() {
        if (userRepository.count() == 0) {
            createMockUsers(new ArrayList<>(List.of(new CreateUserDTO("JohnDoe", "user@user.com", "Password"))));
        }
    }

    private void createMockUsers(List<CreateUserDTO> mockDtos) {
        for (var mockDto : mockDtos) {
            userAccountService.submitRegistration(mockDto);
        }
    }
}
