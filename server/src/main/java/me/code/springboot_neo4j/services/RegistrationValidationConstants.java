package me.code.springboot_neo4j.services;

import org.springframework.stereotype.Component;

@Component
public record RegistrationValidationConstants() {

    // Lower and upper bound for username length
    public final static int USERNAME_MIN_LENGTH = 3;
    public final static int USERNAME_MAX_LENGTH = 14;

    // Lower and upper bound for password length
    public final static int PASSWORD_MIN_LENGTH = 6;
    public final static int PASSWORD_MAX_LENGTH = 17;

    // Field names; used to assign the targeted field when generating validation error details
    public final static String EMAIL_FIELD = "email";
    public final static String USERNAME_FIELD = "username";
    public final static String PASSWORD_FIELD = "password";

    // Regex patterns; used for format validation of user credentials
    public static final String EMAIL_FORMAT_REGEX =
            "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

    public final static String NON_ALPHANUMERIC_REGEX =
            ".*[^a-zA-Z0-9].*";

    public final static String UPPERCASE_REGEX =
            ".*[A-Z].*";

    // Exception error messages; determines the primary message passed as part of exceptions
    public final static String INVALID_USERNAME_ERROR_MESSAGE =
            String.format(
                    "The username must be between %s-%s characters long, " +
                            "and must not include non-alphanumeric characters",
                    USERNAME_MIN_LENGTH, USERNAME_MAX_LENGTH);

    public final static String INVALID_EMAIL_ERROR_MESSAGE =
            "The email must be formatted correctly, for example: example@email.com";

    public final static String INVALID_PASSWORD_ERROR_MESSAGE =
            String.format("The password must be between %s-%s characters long," +
                            " and must include at least one uppercase letter",
                    PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

    // Error details messages; determines the message passed as part of the ValidationErrorDetail
    public final static String INVALID_USERNAME_LENGTH_ERROR_MESSAGE =
            String.format("Must be between %s-%s characters long",
                    USERNAME_MIN_LENGTH, USERNAME_MAX_LENGTH);

    public final static String INVALID_PASSWORD_LENGTH_ERROR_MESSAGE =
            String.format("Must be between %s-%s characters long",
                    PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

    public final static String NON_ALPHANUMERIC_ERROR_MESSAGE =
            "Contains non-alphanumeric characters";

    public final static String NO_UPPERCASE_ERROR_MESSAGE =
            "Does not contain any uppercase characters";

    public final static String NULL_FIELD_ERROR_MESSAGE =
            "The chosen value is null";

    public final static String BLANK_FIELD_ERROR_MESSAGE =
            "The chosen value is blank";

    public final static String UNKNOWN_FIELD_ERROR_MESSAGE =
            "The chosen value is blank";

    public final static String NON_UNIQUE_EMAIL_ERROR_MESSAGE =
            "Is not a unique email";

    public final static String NON_UNIQUE_USERNAME_ERROR_MESSAGE =
            "Is not a unique username";
}
