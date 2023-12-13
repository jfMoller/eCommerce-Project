package me.code.springboot_neo4j.controllers;

import me.code.springboot_neo4j.dto.request.ChangeEmailDTO;
import me.code.springboot_neo4j.dto.request.ChangePasswordDTO;
import me.code.springboot_neo4j.dto.request.ChangeUsernameDTO;
import me.code.springboot_neo4j.dto.request.CreateUserDTO;
import me.code.springboot_neo4j.dto.response.success.Success;
import me.code.springboot_neo4j.models.User;
import me.code.springboot_neo4j.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
public class AccountController {

    private final UserAccountService userAccountService;

    @Autowired
    public AccountController(UserAccountService userAccountService) {
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

}
