package com.mvg.sky.account.controller;

import com.mvg.sky.account.service.session.SessionService;
import com.mvg.sky.common.exception.RequestException;
import com.mvg.sky.common.response.SimpleResponseEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "Session API")
public class SessionController {
    private final SessionService sessionService;

    @GetMapping("/sessions")
    public ResponseEntity<?> getAllSessions(@Nullable @RequestParam("accountId") List<String> accountIds,
                                            @Nullable @RequestParam("sort") List<String> sorts,
                                            @Nullable @RequestParam("offset") Integer offset,
                                            @Nullable @RequestParam("limit") Integer limit) {
        try {
            sorts = sorts == null ? List.of("id") : sorts;
            offset = offset == null ? 0 : offset;
            limit = limit == null ? Integer.MAX_VALUE : limit;

            return ResponseEntity.ok(
                sessionService.getAllSessions(accountIds, sorts, offset, limit)
                    .stream()
                    .toList()
            );
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/sessions/{sessionId}")
    public ResponseEntity<?> getSessionApi(@PathVariable String sessionId) {
        try {
            return ResponseEntity.ok(sessionService.getSessionById(sessionId));
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/sessions/{token}/validate")
    public ResponseEntity<?> validateSessionApi(@PathVariable String token) {
        try {
            boolean isValid = sessionService.validateToken(token);
            return ResponseEntity.ok(Collections.singletonMap("isValid", isValid));
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/sessions")
    public ResponseEntity<?> deleteAllSessionsApi(@Nullable @RequestParam("accountId") List<String> accountIds) {
        try {
            sessionService.clearSessionTable(accountIds);

            return ResponseEntity.ok(
                SimpleResponseEntity.builder()
                     .message("Delete all sessions successfully")
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

    @DeleteMapping("/sessions/{sessionId}")
    public ResponseEntity<?> deleteSessionApi(@PathVariable String sessionId) {
        try {
            sessionService.deleteSessionById(sessionId);

            return ResponseEntity.ok(
                SimpleResponseEntity.builder()
                    .message("Delete session " + sessionId + " successfully")
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
