package com.mvg.sky.account.controller;

import com.mvg.sky.account.service.account.AccountService;
import com.mvg.sky.common.exception.RequestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("accounts")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("login")
    public ResponseEntity<?> loginApi(@RequestBody Object loginRequestEntity) throws RequestException {
        return ResponseEntity.ok("ok");
    }

    @PostMapping("logout")
    public ResponseEntity<?> logoutApi(@RequestBody Object logoutRequestEntity) throws RequestException {
        return ResponseEntity.ok("ok");
    }

    @PostMapping
    public ResponseEntity<?> createApi(@RequestBody Object createAccountRequestEntity) throws RequestException {
        return ResponseEntity.ok("ok");
    }

    @PutMapping("{accountId}")
    public ResponseEntity<?> updateApi(@PathVariable String accountId, @RequestBody Object putAccountRequestEntity) throws RequestException {
        return ResponseEntity.ok("ok");
    }

    @PatchMapping("{accountId}")
    public ResponseEntity<?> patchApi(@PathVariable String accountId, @RequestBody Object patchAccountRequestEntity) throws RequestException {
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("{accountId}")
    public ResponseEntity<?> deleteApi(@PathVariable String accountId) throws RequestException {
        return ResponseEntity.ok("ok");
    }
}
