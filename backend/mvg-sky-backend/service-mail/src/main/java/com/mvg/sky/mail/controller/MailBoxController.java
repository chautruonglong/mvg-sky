package com.mvg.sky.mail.controller;

import com.mvg.sky.common.exception.RequestException;
import com.mvg.sky.mail.dto.request.MailboxCreationRequest;
import com.mvg.sky.mail.service.mailbox.MailboxService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "Mail Box API")
public class MailBoxController {
    private final MailboxService mailboxService;

    @GetMapping("/mailboxes")
    public ResponseEntity<?> getAllMailboxesApi(@NonNull @RequestParam("accountId") String accountId) {
        try {
            return ResponseEntity.ok(mailboxService.getMailboxes(accountId));
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/mailboxes")
    public ResponseEntity<?> createMailboxApi(@Valid @RequestBody MailboxCreationRequest mailboxCreationRequest) {
        try {
            return ResponseEntity.created(URI.create("/api/mailboxes/" + mailboxCreationRequest.getAccountId()))
                .body(mailboxService.createMailbox(mailboxCreationRequest));
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/mailboxes/{accountId}")
    public ResponseEntity<?> deleteMailboxesApi(@PathVariable String accountId, @Nullable @RequestParam String mailbox) {
        try {
            return ResponseEntity.ok(mailboxService.deleteMailbox(accountId, mailbox));
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
