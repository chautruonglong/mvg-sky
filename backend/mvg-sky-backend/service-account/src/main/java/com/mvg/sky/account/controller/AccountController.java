package com.mvg.sky.account.controller;

import com.mvg.sky.account.dto.request.LoginRequest;
import com.mvg.sky.account.exception.UnAuthenticationException;
import com.mvg.sky.account.service.account.AccountService;
import com.mvg.sky.common.exception.RequestException;
import com.mvg.sky.repository.entity.AccountEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Account API")
@AllArgsConstructor
@RestController
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/accounts/login")
    public ResponseEntity<?> loginApi(@Valid @RequestBody LoginRequest loginRequest) throws RequestException {
        try {
            AccountEntity accountEntity = accountService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(accountEntity);
        } catch(Exception exception) {
            throw new UnAuthenticationException(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/accounts/logout")
    public ResponseEntity<?> logoutApi(Object logoutRequestEntity) {
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> createApi(@RequestBody Object createAccountRequestEntity) {
        return ResponseEntity.ok("ok");
    }

    @PutMapping("/accounts/{accountId}")
    public ResponseEntity<?> updateApi(@PathVariable String accountId, @RequestBody Object putAccountRequestEntity)  {
        return ResponseEntity.ok("ok");
    }

    @PatchMapping("/accounts/{accountId}")
    public ResponseEntity<?> patchApi(@PathVariable String accountId, @RequestBody Object patchAccountRequestEntity) {
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/accounts/{accountId}")
    public ResponseEntity<?> deleteApi(@PathVariable String accountId) {
        return ResponseEntity.ok("ok");
    }
}
