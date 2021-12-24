package com.mvg.sky.mail.controller;

import com.mvg.sky.common.exception.RequestException;
import com.mvg.sky.common.response.SimpleResponseEntity;
import com.mvg.sky.mail.dto.request.MailSendingRequest;
import com.mvg.sky.mail.dto.request.MailStatusRequest;
import com.mvg.sky.mail.dto.request.MailsDeletingRequest;
import com.mvg.sky.mail.service.mail.MailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "Mail API")
public class MailController {
    private final MailService mailService;

    @PostMapping(value = "/mails/send", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> sendMailApi(@Valid @ModelAttribute MailSendingRequest mailSendingRequest) {
        try {
            String status = mailService.sendMail(mailSendingRequest);

            return ResponseEntity.ok(
                SimpleResponseEntity.builder()
                    .message(status)
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

    @GetMapping("/mails")
    public ResponseEntity<?> getMailsApi(@NonNull @RequestParam("accountId") String accountId,
                                         @NonNull @RequestParam("mailbox") String mailbox,
                                         @Nullable @RequestParam("sort") List<String> sorts,
                                         @Nullable @RequestParam("offset") Integer offset,
                                         @Nullable @RequestParam("limit") Integer limit) {
        try {
            sorts = sorts == null ? List.of("mailDate") : sorts;
            offset = offset == null ? 0 : offset;
            limit = limit == null ? Integer.MAX_VALUE : limit;

            return ResponseEntity.ok(mailService.fetchMails(accountId, mailbox, sorts, offset, limit));
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/mails/{mailId}")
    public ResponseEntity<?> getMailApi(@PathVariable Long mailId) {
        try {
            return ResponseEntity.ok(mailService.fetchMailById(mailId));
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/mails/change-status/{mailId}")
    public ResponseEntity<?> changeMailStatusApi(@PathVariable Long mailId, @Valid @RequestBody MailStatusRequest mailStatusRequest) {
        try {
            int num = mailService.changeMailStatus(mailId, mailStatusRequest.getStatus());

            return ResponseEntity.ok(
                SimpleResponseEntity.builder()
                    .message("Update status successfully")
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

    @DeleteMapping("/mails/{mailId}")
    public ResponseEntity<?> deleteMailApi(@PathVariable Long mailId) {
        try {
            int num = mailService.deleteMail(mailId);

            return ResponseEntity.ok(
                SimpleResponseEntity.builder()
                    .message("delete email successfully")
                    .recordsChanged(num)
                    .status(HttpStatus.OK.name())
                    .code(HttpStatus.OK.value())
                    .build());
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/mails")
    public ResponseEntity<?> deleteMailsApi(@Valid @RequestBody MailsDeletingRequest mailsDeletingRequest) {
        try {
            int num = mailService.deleteMails(mailsDeletingRequest);

            return ResponseEntity.ok(
                SimpleResponseEntity.builder()
                    .message("delete email successfully")
                    .recordsChanged(num)
                    .status(HttpStatus.OK.name())
                    .code(HttpStatus.OK.value())
                    .build());
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
