package com.mvg.sky.account.controller;

import com.mvg.sky.account.dto.request.LoginRequest;
import com.mvg.sky.account.service.account.AccountService;
import com.mvg.sky.common.exception.RequestException;
import com.mvg.sky.repository.entity.SessionEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "Account API")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/accounts/login")
    public ResponseEntity<?> loginApi(@RequestBody LoginRequest loginRequest) {
        try {
            SessionEntity sessionEntity = accountService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(sessionEntity);
        }
        catch(Exception exception) {
            throw new RequestException(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/accounts/logout")
    public ResponseEntity<?> logoutApi(Object logoutRequestEntity) {
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> createAccountApi(@RequestBody Object createAccountRequestEntity) {
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/accounts")
    public ResponseEntity<?> getAllAccountsApi() {
        return ResponseEntity.ok("ok");
    }

    @PutMapping("/accounts/{accountId}")
    public ResponseEntity<?> updateAccountApi(@PathVariable String accountId, @RequestBody Object putAccountRequestEntity)  {
        return ResponseEntity.ok("ok");
    }

    @PatchMapping("/accounts/{accountId}")
    public ResponseEntity<?> patchAccountApi(@PathVariable String accountId, @RequestBody Object patchAccountRequestEntity) {
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/accounts/{accountId}")
    public ResponseEntity<?> deleteAccountApi(@PathVariable String accountId) {
        return ResponseEntity.ok("ok");
    }
}
