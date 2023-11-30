package me.code.springboot_neo4j.services;

import me.code.springboot_neo4j.dtos.ChangeUsernameDTO;
import me.code.springboot_neo4j.dtos.CreateUserDTO;
import me.code.springboot_neo4j.dtos.ResponseStatusDTO;
import me.code.springboot_neo4j.models.User;
import me.code.springboot_neo4j.models.UserRole;
import me.code.springboot_neo4j.repositories.UserRepository;
import me.code.springboot_neo4j.utils.CredentialsFormatUtil;
import me.code.springboot_neo4j.utils.JsonResponseProvider;
import me.code.springboot_neo4j.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final UserRepository userRepository;

    private final UserService userService;

    private final CredentialsFormatUtil credentialsFormatUtil;

    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AccountService(UserRepository userRepository,
                          UserService userService,
                          CredentialsFormatUtil credentialsFormatUtil,
                                    JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.credentialsFormatUtil = credentialsFormatUtil;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public ResponseEntity<Object> submitRegister(CreateUserDTO dto) {

        ResponseEntity<Object> formattingErrorsResponse =
                findFormattingErrors(dto);

        if (formattingErrorsResponse != null) {
            return formattingErrorsResponse;
        }

        ResponseEntity<Object> preexistingCredentialsResponse =
                findPreexistingCredentials(dto);

        if (preexistingCredentialsResponse != null) {
            return preexistingCredentialsResponse;
        }

        User newUser = new User(
                dto.username(),
                dto.email(),
                dto.password(),
                UserRole.REGULAR_USER);

        userRepository.save(newUser);

        return JsonResponseProvider.sendResponseEntity(
                ResponseStatusDTO.SUCCESS,
                HttpStatus.CREATED,
                dto.email() +
                        " was successfully added as a user.");
    }


    public ResponseEntity<Object> getUserDetails(String userId) {
        User user = userService.loadUserById(userId);
        if (user != null) {
            return JsonResponseProvider.sendUserDetailsEntity(user.getUsername(), user.getEmail());
        }
        return JsonResponseProvider.sendResponseEntity(
                ResponseStatusDTO.ERROR,
                HttpStatus.NOT_FOUND,
                "Requested user details were not found.");

    }

    public ResponseEntity<Object> changeUsername(String userId, ChangeUsernameDTO dto) {

        ResponseEntity<Object> usernameErrors = findUsernameErrors(dto.newUsername());

        if (usernameErrors != null) {
            return usernameErrors;
        }

        User requestedUser = userService.loadUserById(userId);

        if (requestedUser != null) {
            requestedUser.setUsername(dto.newUsername());
            userRepository.save(requestedUser);
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatusDTO.SUCCESS,
                    HttpStatus.OK,
                    "Username changed successfully.");
        }
        return JsonResponseProvider.sendResponseEntity(
                ResponseStatusDTO.ERROR,
                HttpStatus.BAD_REQUEST,
                "Requested user does not exist.");
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