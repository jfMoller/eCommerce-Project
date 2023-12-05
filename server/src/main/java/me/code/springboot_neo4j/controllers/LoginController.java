package me.code.springboot_neo4j.controllers;

import me.code.springboot_neo4j.dto.request.UserLoginDTO;
import me.code.springboot_neo4j.dto.response.success.Success;
import me.code.springboot_neo4j.dto.response.success.variant.AuthenticationSuccess;
import me.code.springboot_neo4j.exceptions.types.UncheckedException;
import me.code.springboot_neo4j.models.User;
import me.code.springboot_neo4j.security.JwtTokenUtil;
import me.code.springboot_neo4j.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
public class LoginController {
    private final UserAccountService userAccountService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public LoginController(UserAccountService userAccountService, JwtTokenUtil jwtTokenUtil) {
        this.userAccountService = userAccountService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<Success> login(@RequestBody UserLoginDTO dto) {
        if (isValidUser(dto)) {
            User user = userAccountService.loadUserByEmail(dto.email());
            String jwtToken = jwtTokenUtil.generateToken(user);

            return new AuthenticationSuccess(
                    HttpStatus.OK,
                    "Login successful",
                    user.getRole().toString(),
                    jwtToken).toResponseEntity();
        } else throw new UncheckedException(HttpStatus.UNAUTHORIZED, "Login failed");
    }

    private boolean isValidUser(UserLoginDTO dto) {
        return userAccountService.isValidUserCredentials(dto.email(), dto.password());
    }

    @GetMapping("/re-authenticate")
    public ResponseEntity<Success> reAuthenticate(@RequestHeader("Authorization") String token) {
        try {
            String userId = jwtTokenUtil.getTokenId(token);
            User user = userAccountService.loadUserById(userId);

            return login(new UserLoginDTO(user.getEmail(), user.getPassword()));

        } catch (Exception exception) {
            throw new UncheckedException(HttpStatus.UNAUTHORIZED, "Re-authentication failed");
        }
    }

    @PostMapping("/confirm")
    public boolean isValidCredentials(@RequestBody UserLoginDTO dto) {
        return userAccountService.isValidUserCredentials(dto.email(), dto.password());
    }

}
