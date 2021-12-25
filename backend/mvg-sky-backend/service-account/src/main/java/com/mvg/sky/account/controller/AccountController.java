package com.mvg.sky.account.controller;

import com.mvg.sky.account.dto.request.AccountCreationRequest;
import com.mvg.sky.account.dto.request.AccountProfileCreationRequest;
import com.mvg.sky.account.dto.request.AccountUpdateRequest;
import com.mvg.sky.account.dto.request.LoginRequest;
import com.mvg.sky.account.dto.request.LogoutRequest;
import com.mvg.sky.account.dto.response.AccountProfileCreationResponse;
import com.mvg.sky.account.service.account.AccountService;
import com.mvg.sky.common.exception.RequestException;
import com.mvg.sky.common.response.SimpleResponseEntity;
import com.mvg.sky.repository.entity.AccountEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
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

    @DeleteMapping("/accounts/{accountId}/logout")
    public ResponseEntity<?> logoutApi(@PathVariable String accountId,
                                       @Valid @RequestBody LogoutRequest logoutRequest,
                                       @Nullable @RequestParam Boolean all) {
        try {
            int num = accountService.logoutAccount(accountId, logoutRequest.getRefreshToken(), all);

            return ResponseEntity.ok(
                SimpleResponseEntity.builder()
                    .message("logout successfully")
                    .recordsChanged(num)
                    .status(HttpStatus.OK.name())
                    .code(HttpStatus.OK.value())
                    .build()
           );
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> createAccountApi(@Valid @RequestBody AccountCreationRequest accountCreationRequest) {
        try {
            AccountEntity accountEntity = accountService.createAccount(accountCreationRequest.getEmail(),
                                                                       accountCreationRequest.getPassword(),
                                                                       accountCreationRequest.getRoles());

            return ResponseEntity.created(URI.create("/api/accounts/" + accountEntity.getId())).body(accountEntity);
        }
        catch(Exception exception) {
            log.info(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/accounts/profile")
    public ResponseEntity<?> createAccountProfileApi(@Valid @RequestBody AccountProfileCreationRequest accountProfileCreationRequest) {
        try {
            AccountProfileCreationResponse accountProfileCreationResponse = accountService.createAccountProfile(accountProfileCreationRequest);

            return ResponseEntity.created(URI.create("/api/accounts/" + accountProfileCreationResponse.getAccount().getId())).body(accountProfileCreationResponse);
        }
        catch(Exception exception) {
            log.info(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts")
    public ResponseEntity<?> getAllAccountsApi(@Nullable @RequestParam("domainId") List<String> domainsIds,
                                               @Nullable @RequestParam("sort") List<String> sorts,
                                               @Nullable @RequestParam("offset") Integer offset,
                                               @Nullable @RequestParam("limit") Integer limit) {
        try {
            sorts = sorts == null ? List.of("id") : sorts;
            offset = offset == null ? 0 : offset;
            limit = limit == null ? Integer.MAX_VALUE : limit;

            return ResponseEntity.ok(accountService.getAllAccounts(domainsIds, sorts, offset, limit));
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/accounts/{accountId}")
    public ResponseEntity<?> patchAccountApi(@PathVariable String accountId, @Valid @RequestBody AccountUpdateRequest accountUpdateRequest) {
        try {
            return ResponseEntity.ok(accountService.updatePartialAccount(accountId, accountUpdateRequest));
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/accounts/{accountId}")
    public ResponseEntity<?> deleteAccountApi(@PathVariable String accountId) {
        try {
            int num = accountService.deleteAccountById(accountId);

            return ResponseEntity.ok(
                SimpleResponseEntity.builder()
                    .message("delete account successfully")
                    .recordsChanged(num)
                    .status(HttpStatus.OK.name())
                    .code(HttpStatus.OK.value())
                    .build()
            );
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
