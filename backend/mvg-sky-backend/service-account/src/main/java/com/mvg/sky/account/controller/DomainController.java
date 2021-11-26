package com.mvg.sky.account.controller;

import com.mvg.sky.account.service.domain.DomainService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "Domain API")
public class DomainController {
    private final DomainService domainService;

    @PostMapping("/domains")
    public ResponseEntity<?> createDomainApi() {
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/domains")
    public ResponseEntity<?> getAllDomainsApi(@Nullable @RequestParam("domain") List<String> domains,
                                              @Nullable @RequestParam("sort") List<String> sorts,
                                              @Nullable @RequestParam("offset") Integer offset,
                                              @Nullable @RequestParam("limit") Integer limit
                                             ) {
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/domains/{domainId}")
    public ResponseEntity<?> getDomainApi(@PathVariable String domainId) {
        return ResponseEntity.ok("ok");
    }

    @PutMapping("/domains/{domainId}")
    public ResponseEntity<?> updateDomainApi(@PathVariable String domainId) {
        return ResponseEntity.ok("ok");
    }

    @PatchMapping("/domains/{domainId}")
    public ResponseEntity<?> updatePartialDomainApi(@PathVariable String domainId) {
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/domains/{domainId}")
    public ResponseEntity<?> deleteDomainApi(@PathVariable String domainId) {
        return ResponseEntity.ok("ok");
    }
}
