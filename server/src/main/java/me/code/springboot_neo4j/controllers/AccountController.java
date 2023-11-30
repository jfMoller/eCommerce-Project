package me.code.springboot_neo4j.controllers;

import me.code.springboot_neo4j.dtos.ChangeEmailDTO;
import me.code.springboot_neo4j.dtos.ChangePasswordDTO;
import me.code.springboot_neo4j.dtos.ChangeUsernameDTO;
import me.code.springboot_neo4j.dtos.CreateUserDTO;
import me.code.springboot_neo4j.services.AccountService;
import me.code.springboot_neo4j.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
public class AccountController {
    private final AccountService accountService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AccountController(AccountService accountService, JwtTokenUtil jwtTokenUtil) {
        this.accountService = accountService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody CreateUserDTO dto) {
        var result = accountService.submitRegister(dto);
        return result;
    }

    @GetMapping("/details")
    public ResponseEntity<Object> getAccountDetails(@RequestHeader("Authorization") String token) {
        String userId = jwtTokenUtil.getTokenId(token);
        var result = accountService.getUserDetails(userId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/username")
    public ResponseEntity<Object> changeUsername(@RequestHeader("Authorization") String token, @RequestBody ChangeUsernameDTO dto) {
        String userId = jwtTokenUtil.getTokenId(token);
        var result = accountService.changeUsername(userId, dto);
        return result;
    }

    @PutMapping("/email")
    public ResponseEntity<Object> changeEmail(@RequestHeader("Authorization") String token, @RequestBody ChangeEmailDTO dto) {
        String userId = jwtTokenUtil.getTokenId(token);
        var result = accountService.changeEmail(userId, dto.newEmail());
        return result;
    }

    @PutMapping("/password")
    public ResponseEntity<Object> changePassword(@RequestHeader("Authorization") String token, @RequestBody ChangePasswordDTO dto) {
        String userId = jwtTokenUtil.getTokenId(token);
        var result = accountService.changePassword(userId, dto.newPassword());
        return result;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteAccount(@RequestHeader("Authorization") String token) {
        String userId = jwtTokenUtil.getTokenId(token);
        return accountService.deleteAccount(userId);
    }

}
