package me.code.springboot_neo4j.controllers;

import me.code.springboot_neo4j.dto.response.success.ResponseStatusDTO;
import me.code.springboot_neo4j.dto.request.UserLoginDTO;
import me.code.springboot_neo4j.models.User;
import me.code.springboot_neo4j.services.UserService;
import me.code.springboot_neo4j.utils.JsonResponseProvider;
import me.code.springboot_neo4j.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
public class LoginController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public LoginController(UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginDTO dto) {
        boolean isValidUser = userService.isValidUserCredentials(dto.email(), dto.password());
        if (isValidUser) {
            User user = userService.loadUserByEmail(dto.email());
            String jwtToken = jwtTokenUtil.generateToken(user.getId(), user.getEmail(), user.getRole().toString());

            return JsonResponseProvider.sendAuthenticationEntity(user.getRole(), jwtToken);
        }
        return JsonResponseProvider.sendResponseEntity(
                ResponseStatusDTO.ERROR, HttpStatus.UNAUTHORIZED, "Invalid email or password.");
    }

    @GetMapping("/re-authenticate")
    public ResponseEntity<Object> reAuthenticate(@RequestHeader("Authorization") String token) {
        String userId = jwtTokenUtil.getTokenId(token);
        User requestedUser = userService.loadUserById(userId);

        if (requestedUser != null) {
            var credentials = new UserLoginDTO(
                    requestedUser.getEmail(), requestedUser.getPassword());
            return login(credentials);
        }

        return JsonResponseProvider.sendResponseEntity(
                ResponseStatusDTO.ERROR,
                HttpStatus.UNAUTHORIZED,
                "User with this _id could not be retrieved, re-authentication failed.");
    }

    @PostMapping("/confirm")
    public boolean isValidCredentials(@RequestBody UserLoginDTO dto) {
       return userService.isValidUserCredentials(dto.email(), dto.password());
    }

}
