package com.mvg.sky.account.controller;

import com.mvg.sky.account.dto.request.ContactCreationRequest;
import com.mvg.sky.account.service.contact.ContactService;
import com.mvg.sky.common.exception.RequestException;
import com.mvg.sky.common.response.SimpleResponseEntity;
import com.mvg.sky.repository.entity.ContactEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@Tag(name = "Contact API")
public class ContactController {
    private final ContactService contactService;

    @GetMapping("/contacts")
    public ResponseEntity<?> getAllContactsApi(@Nullable @RequestParam("profileId") List<String> profileIds,
                                               @Nullable @RequestParam("domainIds") List<String> domainIds,
                                               @Nullable @RequestParam("sort") List<String> sorts,
                                               @Nullable @RequestParam("offset") Integer offset,
                                               @Nullable @RequestParam("limit") Integer limit) {
        try {
            sorts = sorts == null ? List.of("id") : sorts;
            offset = offset == null ? 0 : offset;
            limit = limit == null ? Integer.MAX_VALUE : limit;

            return ResponseEntity.ok(contactService.getAllContacts(profileIds, domainIds,sorts, offset, limit));
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/contacts")
    public ResponseEntity<?> createContactApi(@RequestBody ContactCreationRequest contactCreationRequest) {
        try {
            ContactEntity contactEntity = contactService.addNewContact(contactCreationRequest);

            return ResponseEntity.created(URI.create("/api/contacts/" + contactEntity.getId())).body(contactEntity);
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/contacts")
    public ResponseEntity<?> deleteAllContactsApi(@Nullable @RequestParam("profileId") List<String> profileIds) {
        try {
            int num = contactService.deleteAllContacts(profileIds);

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

    @DeleteMapping("/contacts/{contactId}")
    public ResponseEntity<?> deleteContactApi(@PathVariable String contactId) {
        try {
            int num = contactService.deleteContactById(contactId);

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
