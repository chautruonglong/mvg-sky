package com.mvg.sky.account.controller;

import com.mvg.sky.account.dto.request.DomainCreationRequest;
import com.mvg.sky.account.dto.request.DomainModifyRequest;
import com.mvg.sky.account.service.domain.DomainService;
import com.mvg.sky.common.exception.RequestException;
import com.mvg.sky.common.response.SimpleResponseEntity;
import com.mvg.sky.repository.entity.DomainEntity;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "Domain API")
public class DomainController {
    private final DomainService domainService;

    @PostMapping("/domains")
    public ResponseEntity<?> createDomainApi(@Valid @RequestBody DomainCreationRequest domainCreationRequest) {
        try {
            DomainEntity domainEntity = domainService.createDomain(domainCreationRequest.getName());

            return ResponseEntity.created(URI.create("/api/domains/" + domainEntity.getId())).body(domainEntity);
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/domains")
    public ResponseEntity<?> getAllDomainsApi(@Nullable @RequestParam("sort") List<String> sorts,
                                              @Nullable @RequestParam("offset") Integer offset,
                                              @Nullable @RequestParam("limit") Integer limit
                                             ) {
        try {
            sorts = sorts == null ? List.of("id") : sorts;
            offset = offset == null ? 0 : offset;
            limit = limit == null ? Integer.MAX_VALUE : limit;

            return ResponseEntity.ok(domainService.getAllDomains(sorts, offset, limit));
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/domains/{domainId}")
    public ResponseEntity<?> getDomainApi(@PathVariable String domainId) {
        try {
            return ResponseEntity.ok(domainService.getDomainById(domainId));
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/domains/{domainId}")
    public ResponseEntity<?> updateDomainApi(@PathVariable String domainId, @Valid @RequestBody DomainModifyRequest domainModifyRequest) {
        try {
            return ResponseEntity.ok(domainService.updateDomain(domainId, domainModifyRequest.getName()));
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/domains/{domainId}")
    public ResponseEntity<?> deleteDomainApi(@PathVariable String domainId) {
        try {
            int num = domainService.deleteDomainById(domainId);

            return ResponseEntity.ok(
                SimpleResponseEntity.builder()
                    .message("delete domain successfully")
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
