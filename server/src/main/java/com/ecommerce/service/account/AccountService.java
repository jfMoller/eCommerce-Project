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

    @Autowired
    public AccountService(UserRepository userRepository, UserDetailsService userDetailsService, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public ResponseEntity<Object> submitLogin(LoginCredentials loginCredentials) {

        if (userDetailsService.findUser(loginCredentials) != null) {
            String _id = userDetailsService.findUserId(loginCredentials);
            UserDetails details = userDetailsService.getUserDetails(_id);
            String jwtToken = jwtTokenProvider.generateToken(_id, details.email(), details.role().toString());

            return JsonResponseProvider.sendAuthenticationEntity(details.role(), jwtToken);
        }
        return JsonResponseProvider.sendResponseEntity(
                ResponseStatus.ERROR, HttpStatus.UNAUTHORIZED, "Invalid login credentials.");
    }

    public ResponseEntity<Object> submitRegister(RegisterCredentials registerCredentials) {

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

    private ResponseEntity<Object> findPreexistingCredentials(RegisterCredentials registerCredentials) {
        if (userDetailsService.isExistingUsername(registerCredentials.username())) {
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatus.ERROR,
                    HttpStatus.CONFLICT,
                    "An account with that username already exists.");
        }

        if (userDetailsService.isExistingEmail(registerCredentials.email())) {
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatus.ERROR,
                    HttpStatus.CONFLICT,
                    "An account with that email already exists.");
        }

        return null;
    }

    private ResponseEntity<Object> findFormattingErrors(RegisterCredentials registerCredentials) {
        if (!AccountFormatProvider.isMatchingUsernameFormat(registerCredentials.username())) {
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatus.ERROR,
                    HttpStatus.BAD_REQUEST,
                    "Invalid username; a valid username must be between 3-10 characters long.");
        }

        if (!AccountFormatProvider.isMatchingEmailFormat(registerCredentials.email())) {
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatus.ERROR,
                    HttpStatus.BAD_REQUEST,
                    "Invalid email; valid format example: email@example.com.");
        }

        if (!AccountFormatProvider.isMatchingPasswordFormat(registerCredentials.password())) {
            return JsonResponseProvider.sendResponseEntity(
                    ResponseStatus.ERROR,
                    HttpStatus.BAD_REQUEST,
                    "Invalid password; a valid password must be between 8-20 characters long.");
        }

        return null;
    }

}