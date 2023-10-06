package com.ecommerce.service.account;

import com.ecommerce.auth.JwtTokenProvider;
import com.ecommerce.controller.account.LoginCredentials;
import com.ecommerce.controller.account.RegisterCredentials;
import com.ecommerce.dto.JsonResponseProvider;
import com.ecommerce.dto.ResponseStatus;
import com.ecommerce.entity.Role;
import com.ecommerce.entity.User;
import com.ecommerce.entity.UserDetails;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.user.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final UserRepository userRepository;

    private final UserDetailsService userDetailsService;

    private final JwtTokenProvider jwtTokenProvider;

    private final AccountFormatProvider accountFormatProvider;

    @Autowired
    public AccountService(UserRepository userRepository,
                          UserDetailsService userDetailsService,
                          JwtTokenProvider jwtTokenProvider,
                          AccountFormatProvider accountFormatProvider) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.accountFormatProvider = accountFormatProvider;
    }

    public ResponseEntity<Object> submitLogin(LoginCredentials loginCredentials) {

        if (userDetailsService.findUser(loginCredentials) != null) {
            String _id = userDetailsService.findUserId(loginCredentials);
            UserDetails details = userDetailsService.getUserDetails(_id);
            String jwtToken = jwtTokenProvider.generateToken(_id, details.email(), details.role().toString());

            return JsonResponseProvider.sendAuthenticationEntity(details.role(), jwtToken);
        }
        return JsonResponseProvider.sendResponseEntity(
                ResponseStatus.ERROR, HttpStatus.UNAUTHORIZED, "Invalid email or password.");
    }

    public ResponseEntity<Object> reAuthenticateUser(String token) {
        String user_id = jwtTokenProvider.getToken_id(token);
        User requestedUser = userDetailsService.findUser(user_id);

        if (requestedUser != null) {
            LoginCredentials credentials = new LoginCredentials(
                    requestedUser.getEmail(), requestedUser.getPassword());
            return submitLogin(credentials);
        }

        return JsonResponseProvider.sendResponseEntity(
                ResponseStatus.ERROR,
                HttpStatus.UNAUTHORIZED,
                "User with this _id could not be retrieved, re-authentication failed.");
    }

    public ResponseEntity<Object> submitSignup(RegisterCredentials registerCredentials) {

        ResponseEntity<Object> formattingErrorsResponse =
                findFormattingErrors(registerCredentials);

        if (formattingErrorsResponse != null) {
            return formattingErrorsResponse;
        }

        ResponseEntity<Object> preexistingCredentialsResponse =
                findPreexistingCredentials(registerCredentials);

        if (preexistingCredentialsResponse != null) {
            return preexistingCredentialsResponse;
        }

        User newUser = new User(
                registerCredentials.username(),
                registerCredentials.email(),
                registerCredentials.password(),
                Role.REGULAR_USER);

        userRepository.save(newUser);

        return JsonResponseProvider.sendResponseEntity(
                ResponseStatus.SUCCESS,
                HttpStatus.CREATED,
                registerCredentials.email() +
                        " was successfully added as a user.");
    }

    public boolean isValidLoginCredentials(LoginCredentials loginCredentials) {
        return userDetailsService.findUser(loginCredentials) != null;
    }

    public ResponseEntity<Object> getUserDetails(String token) {
        String user_id = jwtTokenProvider.getToken_id(token);
        UserDetails details = userDetailsService.getUserDetails(user_id);
        if (details != null) {
            return JsonResponseProvider.sendUserDetailsEntity(details.username(), details.email());
        }
        return JsonResponseProvider.sendResponseEntity(
                ResponseStatus.ERROR,
                HttpStatus.NOT_FOUND,
                "Requested user details were not found.");

    }

    public ResponseEntity<Object> changeUsername(String token, String newUsername) {

        ResponseEntity<Object> usernameErrors = findUsernameErrors(newUsername);

        if (usernameErrors != null) {
            return usernameErrors;
        }

        String user_id = jwtTokenProvider.getToken_id(token);
        User requestedUser = userDetailsService.findUser(user_id);

        if (requestedUser != null) {
            requestedUser.setUsername(newUsername);
            userRepository.save(requestedUser);
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatus.SUCCESS,
                    HttpStatus.OK,
                    "Username changed successfully.");
        }
        return JsonResponseProvider.sendResponseEntity(
                ResponseStatus.ERROR,
                HttpStatus.BAD_REQUEST,
                "Requested user does not exist.");
    }

    public ResponseEntity<Object> changeEmail(String token, String newEmail) {

        String user_id = jwtTokenProvider.getToken_id(token);

        ResponseEntity<Object> emailErrors = findEmailErrors(user_id, newEmail);

        if (emailErrors != null) {
            return emailErrors;
        }

        User requestedUser = userDetailsService.findUser(user_id);

        if (requestedUser != null) {
            requestedUser.setEmail(newEmail);
            userRepository.save(requestedUser);
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatus.SUCCESS,
                    HttpStatus.OK,
                    "Email changed successfully.");
        }
        return JsonResponseProvider.sendResponseEntity(
                ResponseStatus.ERROR,
                HttpStatus.BAD_REQUEST,
                "Requested user does not exist.");
    }

    public ResponseEntity<Object> changePassword(String token, String newPassword) {
        String user_id = jwtTokenProvider.getToken_id(token);

        ResponseEntity<Object> passwordErrors = findPasswordErrors(user_id, newPassword);

        if (passwordErrors != null) {
            return passwordErrors;
        }

        User requestedUser = userDetailsService.findUser(user_id);

        if (requestedUser != null) {
            requestedUser.setPassword(newPassword);
            userRepository.save(requestedUser);
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatus.SUCCESS,
                    HttpStatus.OK,
                    "Password changed successfully.");
        }
        return JsonResponseProvider.sendResponseEntity(
                ResponseStatus.ERROR,
                HttpStatus.BAD_REQUEST,
                "Requested user does not exist.");
    }

    public ResponseEntity<Object> deleteAccount(String token) {
        String user_id = jwtTokenProvider.getToken_id(token);
        User requestedUserAccount = userDetailsService.findUser(user_id);

        if (requestedUserAccount == null) {
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatus.ERROR,
                    HttpStatus.BAD_REQUEST,
                    "The requested account does not exist.");
        }

        if (jwtTokenProvider.hasAdminRole(token)) {
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatus.ERROR,
                    HttpStatus.CONFLICT,
                    "An admin account can only be deleted manually in MongoDB.");
        }

        userRepository.deleteById(user_id);

        return JsonResponseProvider.sendResponseEntity(
                ResponseStatus.SUCCESS,
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


    private ResponseEntity<Object> findPreexistingCredentials(RegisterCredentials registerCredentials) {
        if (findExistingUsername(registerCredentials.username()) != null) {
            return findExistingUsername(registerCredentials.username());
        }

        if (findExistingEmail(registerCredentials.email()) != null) {
            return findExistingEmail(registerCredentials.email());
        }

        return null;
    }

    private ResponseEntity<Object> findFormattingErrors(RegisterCredentials registerCredentials) {
        ResponseEntity<Object> userFormattingError = findUsernameFormattingErrors(registerCredentials.username());
        if (userFormattingError != null) {
            return userFormattingError;
        }

        ResponseEntity<Object> emailFormattingError = findEmailFormattingErrors(registerCredentials.email());
        if (emailFormattingError != null) {
            return emailFormattingError;
        }

        ResponseEntity<Object> passwordFormattingError = findPasswordFormattingErrors(registerCredentials.password());
        if (passwordFormattingError != null) {
            return passwordFormattingError;
        }

        return null;
    }

    private ResponseEntity<Object> findExistingUsername(String username) {
        if (userDetailsService.isExistingUsername(username)) {
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatus.ERROR,
                    HttpStatus.CONFLICT,
                    "An account with that username already exists.");
        }
        return null;
    }

    private ResponseEntity<Object> findExistingEmail(String email) {
        if (userDetailsService.isExistingEmail(email)) {
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatus.ERROR,
                    HttpStatus.CONFLICT,
                    "An account with that email already exists.");
        }
        return null;
    }

    private ResponseEntity<Object> findMatchingCurrentPassword(String user_id, String password) {
        if (userDetailsService.isUsersCurrentPassword(user_id, password)) {
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatus.ERROR,
                    HttpStatus.CONFLICT,
                    "Invalid request; you can not change your password to your current password.");
        }
        return null;
    }

    private ResponseEntity<Object> findMatchingCurrentEmail(String user_id, String email) {
        if (userDetailsService.isUsersCurrentEmail(user_id, email)) {
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatus.ERROR,
                    HttpStatus.CONFLICT,
                    "Invalid request; you can not change your email to your current email.");
        }
        return null;
    }

    private ResponseEntity<Object> findUsernameFormattingErrors(String username) {
        if (!accountFormatProvider.isMatchingUsernameFormat(username)) {
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatus.ERROR,
                    HttpStatus.BAD_REQUEST,
                    "Invalid username; a valid username must be between 3-10 characters long without special characters.");
        }
        return null;
    }

    private ResponseEntity<Object> findEmailFormattingErrors(String email) {
        if (!accountFormatProvider.isMatchingEmailFormat(email)) {
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatus.ERROR,
                    HttpStatus.BAD_REQUEST,
                    "Invalid email; valid format example: email@example.com.");
        }
        return null;
    }

    private ResponseEntity<Object> findPasswordFormattingErrors(String password) {
        if (!accountFormatProvider.isMatchingPasswordFormat(password)) {
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatus.ERROR,
                    HttpStatus.BAD_REQUEST,
                    "Invalid password; a valid password must be between 8-20 characters long.");
        }
        return null;
    }


}