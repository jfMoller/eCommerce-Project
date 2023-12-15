package me.code.springboot_neo4j.controllers;

import me.code.springboot_neo4j.dto.request.UserLoginDTO;
import me.code.springboot_neo4j.dto.response.success.Success;
import me.code.springboot_neo4j.dto.response.success.variant.AuthenticationSuccess;
import me.code.springboot_neo4j.exceptions.types.CustomRuntimeException;
import me.code.springboot_neo4j.models.nodes.User;
import me.code.springboot_neo4j.security.JwtTokenUtil;
import me.code.springboot_neo4j.services.LoginValidationService;
import me.code.springboot_neo4j.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
public class LoginController {

    private final AuthenticationProvider authenticationProvider;
    private final UserAccountService userAccountService;
    private final LoginValidationService loginValidationService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public LoginController(
            AuthenticationProvider authenticationProvider,
            UserAccountService userAccountService,
            LoginValidationService loginValidationService,
            JwtTokenUtil jwtTokenUtil) {
        this.authenticationProvider = authenticationProvider;
        this.userAccountService = userAccountService;
        this.loginValidationService = loginValidationService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<Success> login(@RequestBody UserLoginDTO dto) throws Exception {
        try {
            User user = authenticateUser(dto);
            String token = generateTokenForUser(user);

            return new AuthenticationSuccess(
                    HttpStatus.OK,
                    "Login successful",
                    user.getRole().toString(),
                    token)
                    .toResponseEntity();

        } catch (Exception exception) {
            validateCredentials(dto);
            throw new Exception(exception.getMessage());
        }
    }

    private User authenticateUser(UserLoginDTO dto) {
        User user = userAccountService.loadUserByEmail(dto.email());
        Authentication token = new UsernamePasswordAuthenticationToken(user.getUsername(), dto.password());
        Authentication result = authenticationProvider.authenticate(token);

        if (isNotAuthenticated(result)) {
            throw new CustomRuntimeException(HttpStatus.UNAUTHORIZED, "Could not authenticate login request");
        }
        return user;
    }

    private boolean isNotAuthenticated(Authentication result) {
        return !result.isAuthenticated();
    }

    private String generateTokenForUser(User user) {
        return jwtTokenUtil.generateToken(user);
    }

    private void validateCredentials(UserLoginDTO dto) {
        loginValidationService.validateUserCredentials(dto);
    }

    @PostMapping("/confirm")
    public boolean isValidCredentials(@RequestBody UserLoginDTO dto) {
        return userAccountService.isValidUserCredentials(dto.email(), dto.password());
    }

}
