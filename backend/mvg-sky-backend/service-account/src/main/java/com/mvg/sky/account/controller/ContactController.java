package com.mvg.sky.account.controller;

import com.mvg.sky.account.service.contact.ContactService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Tag(name = "Contact API")
public class ContactController {
    private final ContactService contactService;

    @GetMapping("/contacts")
    public ResponseEntity<?> getAllContactsApi(@Nullable @RequestParam("accountId") List<String> accountId,
                                               @Nullable @RequestParam("sort") List<String> sorts,
                                               @Nullable @RequestParam("offset") Integer offset,
                                               @Nullable @RequestParam("limit") Integer limit) {
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/contacts")
    public ResponseEntity<?> createContactApi() {
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/contacts/{contactId}")
    public ResponseEntity<?> deleteContactApi(@PathVariable String contactId) {
        return ResponseEntity.ok("ok");
    }
}
