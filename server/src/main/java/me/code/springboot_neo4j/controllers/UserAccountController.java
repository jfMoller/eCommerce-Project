package me.code.springboot_neo4j.controllers;

import me.code.springboot_neo4j.dtos.requests.*;
import me.code.springboot_neo4j.dtos.responses.success.Success;
import me.code.springboot_neo4j.models.nodes.User;
import me.code.springboot_neo4j.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
public class UserAccountController {

    private final UserAccountService userAccountService;

    @Autowired
    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @PostMapping("/register")
    public ResponseEntity<Success> register(@RequestBody CreateUserDTO dto) {
        var result = userAccountService.submitRegistration(dto);
        return result.toResponseEntity();
    }

    @GetMapping("/details")
    public ResponseEntity<Success> getAccountDetails(@AuthenticationPrincipal User user) {
        var result = userAccountService.getUserDetails(user);
        return result.toResponseEntity();
    }

    @PutMapping("/username")
    public ResponseEntity<Success> changeUsername(@AuthenticationPrincipal User user, @RequestBody ChangeUsernameDTO dto) {
        var result = userAccountService.changeUsername(user, dto);
        return result.toResponseEntity();
    }

    @PutMapping("/email")
    public ResponseEntity<Success> changeEmail(@AuthenticationPrincipal User user, @RequestBody ChangeEmailDTO dto) {
        var result = userAccountService.changeEmail(user, dto);
        return result.toResponseEntity();
    }

    @PutMapping("/password")
    public ResponseEntity<Success> changePassword(@AuthenticationPrincipal User user, @RequestBody ChangePasswordDTO dto) {
        var result = userAccountService.changePassword(user, dto);
        return result.toResponseEntity();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Success> deleteAccount(@AuthenticationPrincipal User user) {
        var result = userAccountService.deleteAccount(user);
        return result.toResponseEntity();
    }

    @PostMapping("/confirm")
    public boolean isValidCredentials(@RequestBody UserLoginDTO dto) {
        return userAccountService.isValidUserCredentials(dto.email(), dto.password());
    }

}
