package com.mvg.sky.mail.controller;

import com.mvg.sky.common.exception.RequestException;
import com.mvg.sky.mail.service.mailbox.MailboxService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "Mail Box API")
public class MailBoxController {
    private final MailboxService mailboxService;

    @GetMapping("/mailboxes")
    public ResponseEntity<?> getAllMailboxesApi(@Nullable @RequestParam("accountId") List<String> accountIds,
                                                @Nullable @RequestParam("sort") List<String> sorts,
                                                @Nullable @RequestParam("offset") Integer offset,
                                                @Nullable @RequestParam("limit") Integer limit) {
        try {
            sorts = sorts == null ? List.of("createdAt") : sorts;
            offset = offset == null ? 0 : offset;
            limit = limit == null ? Integer.MAX_VALUE : limit;

            return ResponseEntity.ok("ok");
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/mailboxes")
    public ResponseEntity<?> createMailboxApi() {
        try {

            return ResponseEntity.ok("ok");
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/mailboxes/{mailboxesId}")
    public ResponseEntity<?> deleteMailboxesApi(@PathVariable String mailboxesId) {
        try {

            return ResponseEntity.ok("ok");
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/mailboxes/{mailboxesId}")
    public ResponseEntity<?> updatePartialMailboxesApi(@PathVariable String mailboxesId) {
        try {

            return ResponseEntity.ok("ok");
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
