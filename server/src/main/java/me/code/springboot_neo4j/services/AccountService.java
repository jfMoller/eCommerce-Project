package me.code.springboot_neo4j.services;

import me.code.springboot_neo4j.dto.request.ChangeUsernameDTO;
import me.code.springboot_neo4j.dto.request.CreateUserDTO;
import me.code.springboot_neo4j.dto.response.success.ResponseStatusDTO;
import me.code.springboot_neo4j.dto.response.success.Success;
import me.code.springboot_neo4j.dto.response.success.variant.UserDetailsSuccess;
import me.code.springboot_neo4j.exceptions.types.AccountRegistrationException;
import me.code.springboot_neo4j.exceptions.types.CouldNotFindUserDetailsException;
import me.code.springboot_neo4j.models.User;
import me.code.springboot_neo4j.models.UserRole;
import me.code.springboot_neo4j.repositories.UserRepository;
import me.code.springboot_neo4j.utils.CredentialsFormatUtil;
import me.code.springboot_neo4j.utils.CredentialsValidatorUtil;
import me.code.springboot_neo4j.utils.JsonResponseProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final UserRepository userRepository;

    private final UserService userService;

    private final CredentialsValidatorUtil credentialsValidator;

    private final CredentialsFormatUtil credentialsFormatUtil;

    @Autowired
    public AccountService(UserRepository userRepository,
                          UserService userService,
                          CredentialsValidatorUtil credentialsValidator,
                          CredentialsFormatUtil credentialsFormatUtil) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.credentialsValidator = credentialsValidator;
        this.credentialsFormatUtil = credentialsFormatUtil;
    }

    public Success submitRegistration(CreateUserDTO dto) {
        checkForValidationErrors(dto.email(), dto.username(), dto.password());
        try {
            User newUser = new User(dto.email(), dto.username(), dto.password(), UserRole.REGULAR_USER);
            userRepository.save(newUser);

            return new Success(
                    HttpStatus.CREATED,
                    "Successfully registered a new account");

        } catch (Exception exception) {
            throw new AccountRegistrationException("Failed to register a new account: " + exception.getMessage());
        }
    }

    private void checkForValidationErrors(String email, String username, String password) {
        credentialsValidator.findFormattingErrors(email, username, password);
        credentialsValidator.findNonUniqueValues(email, username);
    }


    public Success getUserDetails(String userId) {
        try {
            User user = userService.loadUserById(userId);

            return new UserDetailsSuccess(HttpStatus.OK,
                    "User details were successfully retrieved",
                    user.getEmail(), user.getUsername());

        } catch (Exception exception) {
            throw new CouldNotFindUserDetailsException(
                    "Could not find user details for user with id: {" + userId + "}");
        }
    }

    public Success changeUsername(String userId, ChangeUsernameDTO dto) {
        credentialsValidator.findNullUsername(dto.newUsername());
        credentialsValidator.findUsernameFormattingError(dto.newUsername());

        try {
            User user = userService.loadUserById(userId);
            user.setUsername(dto.newUsername());
            userRepository.save(user);

            return new Success(
                    HttpStatus.CREATED,
                    "The username was successfully changed");

        } catch (Exception exception) {
            System.out.println("3");
            throw new CouldNotFindUserDetailsException(
                    "Could not find user details for user with id: {" + userId + "}");
        }
    }

    public ResponseEntity<Object> changeEmail(String userId, String newEmail) {

        ResponseEntity<Object> emailErrors = findEmailErrors(userId, newEmail);

        if (emailErrors != null) {
            return emailErrors;
        }

        User requestedUser = userService.loadUserById(userId);

        if (requestedUser != null) {
            requestedUser.setEmail(newEmail);
            userRepository.save(requestedUser);
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatusDTO.SUCCESS,
                    HttpStatus.OK,
                    "Email changed successfully.");
        }
        return JsonResponseProvider.sendResponseEntity(
                ResponseStatusDTO.ERROR,
                HttpStatus.BAD_REQUEST,
                "Requested user does not exist.");
    }

    public ResponseEntity<Object> changePassword(String userId, String newPassword) {

        ResponseEntity<Object> passwordErrors = findPasswordErrors(userId, newPassword);

        if (passwordErrors != null) {
            return passwordErrors;
        }

        User requestedUser = userService.loadUserById(userId);

        if (requestedUser != null) {
            requestedUser.setPassword(newPassword);
            userRepository.save(requestedUser);
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatusDTO.SUCCESS,
                    HttpStatus.OK,
                    "Password changed successfully.");
        }
        return JsonResponseProvider.sendResponseEntity(
                ResponseStatusDTO.ERROR,
                HttpStatus.BAD_REQUEST,
                "Requested user does not exist.");
    }

    public ResponseEntity<Object> deleteAccount(String userId) {
        User requestedUserAccount = userService.loadUserById(userId);

        if (requestedUserAccount == null) {
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatusDTO.ERROR,
                    HttpStatus.BAD_REQUEST,
                    "The requested account does not exist.");
        }

        userRepository.deleteById(userId);

        return JsonResponseProvider.sendResponseEntity(
                ResponseStatusDTO.SUCCESS,
                HttpStatus.OK,
                "The account was deleted successfully.");

    }

    private ResponseEntity<Object> findUsernameErrors(String username) {
        ResponseEntity<Object> existingUsernameError = findExistingUsername(username);

        if (existingUsernameError != null) {
            return existingUsernameError;
        }

        ResponseEntity<Object> userFormattingError = findUsernameFormattingErrors(username);
        if (userFormattingError != null) {
            return userFormattingError;
        }

        return null;
    }

    private ResponseEntity<Object> findEmailErrors(String user_id, String email) {

        ResponseEntity<Object> emailFormattingError = findEmailFormattingErrors(email);
        if (emailFormattingError != null) {
            return emailFormattingError;
        }

        ResponseEntity<Object> currentEmailMatch = findMatchingCurrentEmail(user_id, email);
        if (currentEmailMatch != null) {
            return currentEmailMatch;
        }

        ResponseEntity<Object> nonUniqueEmailMatch = findExistingEmail(email);
        if (nonUniqueEmailMatch != null) {
            return nonUniqueEmailMatch;
        }

        return null;
    }

    private ResponseEntity<Object> findPasswordErrors(String user_id, String password) {
        ResponseEntity<Object> passwordFormattingError = findPasswordFormattingErrors(password);
        if (passwordFormattingError != null) {
            return passwordFormattingError;
        }

        ResponseEntity<Object> currentPasswordMatch = findMatchingCurrentPassword(user_id, password);
        if (currentPasswordMatch != null) {
            return currentPasswordMatch;
        }

        return null;
    }


    private ResponseEntity<Object> findPreexistingCredentials(CreateUserDTO dto) {
        if (findExistingUsername(dto.username()) != null) {
            return findExistingUsername(dto.username());
        }

        if (findExistingEmail(dto.email()) != null) {
            return findExistingEmail(dto.email());
        }

        return null;
    }

    private ResponseEntity<Object> findFormattingErrors(CreateUserDTO dto) {
        ResponseEntity<Object> userFormattingError = findUsernameFormattingErrors(dto.username());
        if (userFormattingError != null) {
            return userFormattingError;
        }

        ResponseEntity<Object> emailFormattingError = findEmailFormattingErrors(dto.email());
        if (emailFormattingError != null) {
            return emailFormattingError;
        }

        ResponseEntity<Object> passwordFormattingError = findPasswordFormattingErrors(dto.password());
        if (passwordFormattingError != null) {
            return passwordFormattingError;
        }

        return null;
    }

    private ResponseEntity<Object> findExistingUsername(String username) {
        if (userService.isExistingUsername(username)) {
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatusDTO.ERROR,
                    HttpStatus.CONFLICT,
                    "An account with that username already exists.");
        }
        return null;
    }

    private ResponseEntity<Object> findExistingEmail(String email) {
        if (userService.isExistingEmail(email)) {
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatusDTO.ERROR,
                    HttpStatus.CONFLICT,
                    "An account with that email already exists.");
        }
        return null;
    }

    private ResponseEntity<Object> findMatchingCurrentPassword(String user_id, String password) {
        if (userService.isUsersCurrentPassword(user_id, password)) {
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatusDTO.ERROR,
                    HttpStatus.CONFLICT,
                    "Invalid request; you can not change your password to your current password.");
        }
        return null;
    }

    private ResponseEntity<Object> findMatchingCurrentEmail(String user_id, String email) {
        if (userService.isUsersCurrentEmail(user_id, email)) {
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatusDTO.ERROR,
                    HttpStatus.CONFLICT,
                    "Invalid request; you can not change your email to your current email.");
        }
        return null;
    }

    private ResponseEntity<Object> findUsernameFormattingErrors(String username) {
        if (!credentialsFormatUtil.isMatchingUsernameFormat(username)) {
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatusDTO.ERROR,
                    HttpStatus.BAD_REQUEST,
                    "Invalid username; a valid username must be between 3-10 characters long without special characters.");
        }
        return null;
    }

    private ResponseEntity<Object> findEmailFormattingErrors(String email) {
        if (!credentialsFormatUtil.isMatchingEmailFormat(email)) {
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatusDTO.ERROR,
                    HttpStatus.BAD_REQUEST,
                    "Invalid email; valid format example: email@example.com.");
        }
        return null;
    }

    private ResponseEntity<Object> findPasswordFormattingErrors(String password) {
        if (!credentialsFormatUtil.isMatchingPasswordFormat(password)) {
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatusDTO.ERROR,
                    HttpStatus.BAD_REQUEST,
                    "Invalid password; a valid password must be between 8-20 characters long.");
        }
        return null;
    }


}