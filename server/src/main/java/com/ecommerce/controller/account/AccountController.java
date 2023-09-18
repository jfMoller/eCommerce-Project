package com.ecommerce.controller.account;

import com.ecommerce.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginCredentials loginCredentials) {
        return accountService.submitLogin(loginCredentials);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterCredentials registerCredentials) {
        return accountService.submitRegister(registerCredentials);
    }
}
