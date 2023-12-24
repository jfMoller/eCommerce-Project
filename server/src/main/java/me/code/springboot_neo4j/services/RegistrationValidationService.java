package me.code.springboot_neo4j.services;

import me.code.springboot_neo4j.dtos.responses.error.details.ValidationErrorDetail;
import me.code.springboot_neo4j.exceptions.types.CustomRuntimeException;
import me.code.springboot_neo4j.exceptions.types.variants.ValidationException;
import me.code.springboot_neo4j.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static me.code.springboot_neo4j.services.RegistrationValidationConstants.*;

@Service
public class RegistrationValidationService {

    private final UserRepository userRepository;

    @Autowired
    public RegistrationValidationService(
            UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void findFormattingErrors(String email, String username, String password) {
        findNullValues(email, username, password);

        findEmailFormattingError(email);
        findUsernameFormattingError(username);
        findPasswordFormattingError(password);
    }

    public void findEmailFormattingError(String email) {
        if (hasEmailFormattingError(email)) {
            throw new ValidationException(
                    HttpStatus.BAD_REQUEST,
                    INVALID_EMAIL_ERROR_MESSAGE,
                    generateValidationErrorDetail(EMAIL_FIELD, email));
        }
    }

    public void findUsernameFormattingError(String username) {
        if (hasUsernameFormattingError(username)) {
            throw new ValidationException(
                    HttpStatus.BAD_REQUEST,
                    INVALID_USERNAME_ERROR_MESSAGE,
                    generateValidationErrorDetail(USERNAME_FIELD, username));
        }
    }

    public void findPasswordFormattingError(String password) {
        if (hasPasswordFormattingError(password)) {
            throw new ValidationException(
                    HttpStatus.BAD_REQUEST,
                    INVALID_PASSWORD_ERROR_MESSAGE,
                    generateValidationErrorDetail(PASSWORD_FIELD, password));
        }
    }

    public void findNullValues(String email, String username, String password) {
        findNullEmail(email);
        findNullUsername(username);
        findNullPassword(password);
    }

    public void findNullEmail(String email) {
        if (email == null) {
            throw new ValidationException(
                    HttpStatus.BAD_REQUEST,
                    INVALID_EMAIL_ERROR_MESSAGE,
                    generateValidationErrorDetail(EMAIL_FIELD, null));
        }
    }

    public void findNullPassword(String password) {
        if (password == null) {
            throw new ValidationException(
                    HttpStatus.BAD_REQUEST,
                    INVALID_PASSWORD_ERROR_MESSAGE,
                    generateValidationErrorDetail(PASSWORD_FIELD, null));
        }
    }

    public void findNullUsername(String username) {
        if (username == null) {
            throw new ValidationException(
                    HttpStatus.BAD_REQUEST,
                    INVALID_USERNAME_ERROR_MESSAGE,
                    generateValidationErrorDetail(USERNAME_FIELD, null));
        }
    }

    public boolean hasEmailFormattingError(String email) {
        return email.isBlank() || isNonEmailFormat(email);
    }

    public boolean hasUsernameFormattingError(String username) {
        return username.isBlank() ||
                hasNonAlphanumericCharacters(username) ||
                isInvalidUsernameLength(username);
    }


    public boolean hasPasswordFormattingError(String password) {
        return password.isBlank() ||
                doesNotContainUppercase(password) ||
                isInvalidPasswordLength(password);
    }

    public boolean isNonEmailFormat(String string) {
        System.out.println(string.matches(EMAIL_FORMAT_REGEX));
        return !string.matches(EMAIL_FORMAT_REGEX);
    }

    public boolean hasNonAlphanumericCharacters(String string) {
        return string.matches(NON_ALPHANUMERIC_REGEX);
    }

    private boolean isInvalidLength(String string, int minLength, int maxLength) {
        int length = string.length();
        return length < minLength || length > maxLength;
    }

    private boolean isInvalidUsernameLength(String username) {
        return isInvalidLength(username, USERNAME_MIN_LENGTH, USERNAME_MAX_LENGTH);
    }

    private boolean isInvalidPasswordLength(String password) {
        return isInvalidLength(password, PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);
    }

    private boolean doesNotContainUppercase(String string) {
        return !string.matches(UPPERCASE_REGEX);
    }

    public void findNonUniqueValues(String email, String username) {
        findNonUniqueEmail(email);
        findNonUniqueUsername(username);
    }

    public void findNonUniqueEmail(String email) {
        if (isNonUniqueEmail(email)) {
            throw new ValidationException(
                    HttpStatus.CONFLICT,
                    "An account with the chosen email already exists",
                    new ValidationErrorDetail(
                            "user",
                            "email",
                            email,
                            NON_UNIQUE_EMAIL_ERROR_MESSAGE));
        }
    }

    public void findNonUniqueUsername(String username) {
        if (isNonUniqueUsername(username)) {
            throw new ValidationException(
                    HttpStatus.CONFLICT,
                    "An account with the chosen username already exists",
                    new ValidationErrorDetail(
                            "user",
                            "username",
                            username,
                            NON_UNIQUE_USERNAME_ERROR_MESSAGE));
        }
    }

    private boolean isNonUniqueEmail(String email) {
        return userRepository.isExistingEmail(email);
    }

    private boolean isNonUniqueUsername(String username) {
        return userRepository.isExistingUsername(username);
    }

    private ValidationErrorDetail generateValidationErrorDetail(String type, String value) {
        String field;
        String errorMessage;
        boolean isNullValue = (value == null);
        boolean isBlankValue = (value != null && value.isBlank());

        switch (type) {
            case "email" -> {
                field = "email";
                errorMessage =
                        isNullValue ? NULL_FIELD_ERROR_MESSAGE
                                : isBlankValue ? BLANK_FIELD_ERROR_MESSAGE
                                : isNonEmailFormat(value) ? INVALID_EMAIL_ERROR_MESSAGE
                                : UNKNOWN_FIELD_ERROR_MESSAGE;
            }
            case "username" -> {
                field = "username";
                errorMessage =
                        isNullValue ? NULL_FIELD_ERROR_MESSAGE
                                : isBlankValue ? BLANK_FIELD_ERROR_MESSAGE
                                : isInvalidUsernameLength(value) ? INVALID_USERNAME_LENGTH_ERROR_MESSAGE
                                : hasNonAlphanumericCharacters(value) ? NON_ALPHANUMERIC_ERROR_MESSAGE
                                : UNKNOWN_FIELD_ERROR_MESSAGE;
            }
            case "password" -> {
                field = "password";
                errorMessage =
                        isNullValue ? NULL_FIELD_ERROR_MESSAGE
                                : isBlankValue ? BLANK_FIELD_ERROR_MESSAGE
                                : isInvalidPasswordLength(value) ? INVALID_PASSWORD_LENGTH_ERROR_MESSAGE
                                : doesNotContainUppercase(value) ? NO_UPPERCASE_ERROR_MESSAGE
                                : UNKNOWN_FIELD_ERROR_MESSAGE;
            }
            default -> throw new CustomRuntimeException(
                    HttpStatus.BAD_REQUEST,
                    "Could not generate validation error detail");
        }

        return new ValidationErrorDetail(errorMessage, "user", field, (!type.equals("password") ? value : "Hidden"));
    }

}
