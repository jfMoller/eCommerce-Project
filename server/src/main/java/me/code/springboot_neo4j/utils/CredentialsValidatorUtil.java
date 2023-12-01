package me.code.springboot_neo4j.utils;

import me.code.springboot_neo4j.dto.response.error.variant.ValidationErrorDetail;
import me.code.springboot_neo4j.exceptions.types.EmailValidationException;
import me.code.springboot_neo4j.exceptions.types.NonUniqueValueException;
import me.code.springboot_neo4j.exceptions.types.PasswordValidationException;
import me.code.springboot_neo4j.exceptions.types.UsernameValidationException;
import me.code.springboot_neo4j.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CredentialsValidatorUtil {

    // Lower and upper bound for username length
    private final static int USERNAME_MIN_LENGTH = 3;
    private final static int USERNAME_MAX_LENGTH = 14;

    // Lower and upper bound for password length
    private final static int PASSWORD_MIN_LENGTH = 6;
    private final static int PASSWORD_MAX_LENGTH = 17;

    // Field names; used to assign the targeted field when generating validation error details
    private final static String EMAIL_FIELD = "email";
    private final static String USERNAME_FIELD = "username";
    private final static String PASSWORD_FIELD = "password";

    // Regex patterns; used for format validation of user credentials
    private static final String EMAIL_FORMAT_REGEX = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    private final static String NON_ALPHANUMERIC_REGEX = ".*[^a-zA-Z0-9].*";
    private final static String UPPERCASE_REGEX = ".*[A-Z].*";

    // Exception error messages; determines the primary message passed as part of exceptions
    private final static String INVALID_USERNAME_ERROR_MESSAGE =
            String.format("The username must be between %s-%s characters long, and must not include non-alphanumeric characters",
                    USERNAME_MIN_LENGTH, USERNAME_MAX_LENGTH);

    private final static String INVALID_EMAIL_ERROR_MESSAGE = "The email must be formatted correctly, for example: example@email.com";

    private final static String INVALID_PASSWORD_ERROR_MESSAGE =
            String.format("The password must be between %s-%s characters long, and must include at least one uppercase letter",
                    PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

    // Error details messages; determines the message passed as part of the ValidationErrorDetail
    private final static String INVALID_USERNAME_LENGTH_ERROR_MESSAGE =
            String.format("Must be between %s-%s characters long", USERNAME_MIN_LENGTH, USERNAME_MAX_LENGTH);

    private final static String INVALID_PASSWORD_LENGTH_ERROR_MESSAGE =
            String.format("Must be between %s-%s characters long", PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

    private final static String NON_ALPHANUMERIC_ERROR_MESSAGE = "Contains non-alphanumeric characters";

    private final static String NO_UPPERCASE_ERROR_MESSAGE = "Does not contain any uppercase characters";

    private final static String NULL_FIELD_ERROR_MESSAGE = "The chosen value is null";

    private final static String BLANK_FIELD_ERROR_MESSAGE = "The chosen value is blank";

    private final static String UNKNOWN_FIELD_ERROR_MESSAGE = "The chosen value is blank";

    private final static String NON_UNIQUE_EMAIL_ERROR_MESSAGE = "Is not a unique email";

    private final static String NON_UNIQUE_USERNAME_ERROR_MESSAGE = "Is not a unique username";

    private final UserRepository userRepository;

    @Autowired
    public CredentialsValidatorUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void findFormattingErrors(String email, String username, String password) {
        findNullValues(email, username, password);

        if (hasEmailFormattingError(email)) {
            throw new EmailValidationException(
                    INVALID_EMAIL_ERROR_MESSAGE,
                    generateValidationErrorDetail(EMAIL_FIELD, email));
        }
        if (hasUsernameFormattingError(username)) {
            throw new UsernameValidationException(
                    INVALID_USERNAME_ERROR_MESSAGE,
                    generateValidationErrorDetail(USERNAME_FIELD, username));
        }
        if (hasPasswordFormattingError(password)) {
            throw new PasswordValidationException(
                    INVALID_PASSWORD_ERROR_MESSAGE,
                    generateValidationErrorDetail(PASSWORD_FIELD, password));
        }
    }

    public void findNullValues(String email, String username, String password) {
        if (email == null) {
            throw new EmailValidationException(
                    INVALID_EMAIL_ERROR_MESSAGE,
                    generateValidationErrorDetail(EMAIL_FIELD, null));

        }
        if (username == null) {
            throw new UsernameValidationException(
                    INVALID_USERNAME_ERROR_MESSAGE,
                    generateValidationErrorDetail(USERNAME_FIELD, null));

        }
        if (password == null) {
            throw new PasswordValidationException(
                    INVALID_PASSWORD_ERROR_MESSAGE,
                    generateValidationErrorDetail(PASSWORD_FIELD, null));

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
            throw new NonUniqueValueException(
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
            throw new NonUniqueValueException(
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
            default -> throw new IllegalArgumentException("Invalid variant: " + type);
        }

        return new ValidationErrorDetail("user", field, value, errorMessage);
    }

}
