package me.code.springboot_neo4j.controllers;

import me.code.springboot_neo4j.dto.request.ChangeEmailDTO;
import me.code.springboot_neo4j.dto.request.ChangePasswordDTO;
import me.code.springboot_neo4j.dto.request.ChangeUsernameDTO;
import me.code.springboot_neo4j.dto.request.CreateUserDTO;
import me.code.springboot_neo4j.dto.response.success.Success;
import me.code.springboot_neo4j.security.JwtTokenUtil;
import me.code.springboot_neo4j.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
public class AccountController {
    private final UserAccountService userAccountService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AccountController(UserAccountService userAccountService, JwtTokenUtil jwtTokenUtil) {
        this.userAccountService = userAccountService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<Success> register(@RequestBody CreateUserDTO dto) {
        var result = userAccountService.submitRegistration(dto);
        return result.toResponseEntity();
    }

    @GetMapping("/details")
    public ResponseEntity<Success> getAccountDetails(@RequestHeader("Authorization") String token) {
        String userId = jwtTokenUtil.getTokenId(token);
        var result = userAccountService.getUserDetails(userId);
        return result.toResponseEntity();
    }

    @PutMapping("/username")
    public ResponseEntity<Success> changeUsername(@RequestHeader("Authorization") String token, @RequestBody ChangeUsernameDTO dto) {
        String userId = jwtTokenUtil.getTokenId(token);
        var result = userAccountService.changeUsername(userId, dto);
        return result.toResponseEntity();
    }

    @PutMapping("/email")
    public ResponseEntity<Success> changeEmail(@RequestHeader("Authorization") String token, @RequestBody ChangeEmailDTO dto) {
        String userId = jwtTokenUtil.getTokenId(token);
        var result = userAccountService.changeEmail(userId, dto);
        return result.toResponseEntity();
    }

    @PutMapping("/password")
    public ResponseEntity<Success> changePassword(@RequestHeader("Authorization") String token, @RequestBody ChangePasswordDTO dto) {
        String userId = jwtTokenUtil.getTokenId(token);
        var result = userAccountService.changePassword(userId, dto);
        return result.toResponseEntity();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Success> deleteAccount(@RequestHeader("Authorization") String token) {
        String userId = jwtTokenUtil.getTokenId(token);
        var result = userAccountService.deleteAccount(userId);
        return result.toResponseEntity();
    }

}
