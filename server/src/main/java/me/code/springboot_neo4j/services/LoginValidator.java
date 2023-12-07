package me.code.springboot_neo4j.services;

import me.code.springboot_neo4j.dto.request.UserLoginDTO;
import me.code.springboot_neo4j.dto.response.error.detailvariant.ValidationErrorDetail;
import me.code.springboot_neo4j.exceptions.types.variant.ValidationException;
import me.code.springboot_neo4j.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class LoginValidator {
    private final UserRepository userRepository;

    @Autowired
    public LoginValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateUserCredentials(UserLoginDTO dto) {
        String email = dto.email();
        String password = dto.password();

        if (isInvalidEmail(email)) {
            throw new ValidationException(
                    HttpStatus.BAD_REQUEST,
                    "You have entered an invalid email",
                    getValidationErrorDetail(email));

        } else if (isInvalidPassword(email, password)) {
            throw new ValidationException(
                    HttpStatus.BAD_REQUEST,
                    "You have entered an invalid password",
                    getValidationErrorDetail());
        }
    }

    private boolean isInvalidEmail(String email) {
        return userRepository.isInvalidEmail(email);
    }

    private boolean isInvalidPassword(String email, String password) {
        return userRepository.isInvalidPassword(email, password);
    }

    private ValidationErrorDetail getValidationErrorDetail() {
        return new ValidationErrorDetail(
                "JSON",
                "password",
                "{hidden}",
                "Is not a valid password");
    }

    private ValidationErrorDetail getValidationErrorDetail(String email) {
        return new ValidationErrorDetail(
                "JSON",
                "email",
                email,
                "Is not a valid email");
    }
}
