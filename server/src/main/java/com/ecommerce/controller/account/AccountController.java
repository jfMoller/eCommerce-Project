package com.ecommerce.controller.account;

import com.ecommerce.auth.JwtAuthProvider;
import com.ecommerce.dto.ChangePasswordRequest;
import com.ecommerce.dto.ChangeUsernameRequest;
import com.ecommerce.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    private final JwtAuthProvider jwtAuthProvider;

    @Autowired
    public AccountController(AccountService accountService, JwtAuthProvider jwtAuthProvider) {
        this.accountService = accountService;
        this.jwtAuthProvider = jwtAuthProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginCredentials loginCredentials) {
        return accountService.submitLogin(loginCredentials);
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> register(@RequestBody RegisterCredentials registerCredentials) {
        return accountService.submitSignup(registerCredentials);
    }

    @GetMapping("/details")
    public ResponseEntity<Object> getUserDetails(@RequestHeader("Authorization") String token) {
        return jwtAuthProvider.authorizeAccess(token, () -> accountService.getUserDetails(token));
    }

    @PostMapping("/confirm")
    public boolean isValidLoginCredentials(@RequestHeader("Authorization") String token,
                                           @RequestBody LoginCredentials loginCredentials) {
        return jwtAuthProvider.authorizeAccess(token,
                () -> accountService.isValidLoginCredentials(loginCredentials));
    }

    @PutMapping("/username")
    public ResponseEntity<Object> changeUsername(@RequestHeader("Authorization") String token,
                                                 @RequestBody ChangeUsernameRequest request) {
        return jwtAuthProvider.authorizeAccess(token,
                () -> accountService.changeUsername(token, request.getNewUsername()));
    }

    @PutMapping("/email")
    public ResponseEntity<Object> changeEmail(@RequestHeader("Authorization") String token) {
        return ResponseEntity.badRequest().body("Not implemented.");
    }

    @PutMapping("/password")
    public ResponseEntity<Object> changePassword(@RequestHeader("Authorization") String token,
                                                 @RequestBody ChangePasswordRequest request) {
        return jwtAuthProvider.authorizeAccess(token,
                () -> accountService.changePassword(token, request.getNewPassword()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteAccount(@RequestHeader("Authorization") String token) {
        return ResponseEntity.badRequest().body("Not implemented.");
    }

}
