package com.mvg.sky.account.controller;

import com.mvg.sky.account.dto.request.AccountCreationRequest;
import com.mvg.sky.account.dto.request.LoginRequest;
import com.mvg.sky.account.service.account.AccountService;
import com.mvg.sky.common.exception.RequestException;
import com.mvg.sky.repository.entity.AccountEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "Account API")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/accounts/login")
    public ResponseEntity<?> loginApi(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            return ResponseEntity.ok(accountService.authenticate(loginRequest.getEmail(), loginRequest.getPassword()));
        }
        catch(Exception exception) {
            log.error("{} {}", loginRequest.getEmail(), exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/accounts/logout")
    public ResponseEntity<?> logoutApi(Object logoutRequestEntity) {
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> createAccountApi(@Valid @RequestBody AccountCreationRequest accountCreationRequest) {
        try {
            AccountEntity accountEntity = accountService.createAccount(accountCreationRequest.getEmail(),
                                                                       accountCreationRequest.getPassword(),
                                                                       accountCreationRequest.getRoles());
            return ResponseEntity.ok(accountEntity);
        }
        catch(Exception exception) {
            log.info(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts")
    public ResponseEntity<?> getAllAccountsApi(@Nullable @RequestParam("accountId") List<String> accountIds,
                                               @Nullable @RequestParam("sort") List<String> sorts,
                                               @Nullable @RequestParam("offset") Integer offset,
                                               @Nullable @RequestParam("limit") Integer limit) {
        try {
            return ResponseEntity.ok("ok");
        }
        catch(Exception exception) {
            log.info(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
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
