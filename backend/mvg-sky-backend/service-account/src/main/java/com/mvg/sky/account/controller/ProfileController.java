package com.mvg.sky.account.controller;

import com.mvg.sky.account.dto.request.ProfileCreationRequest;
import com.mvg.sky.account.dto.request.ProfileModifyRequest;
import com.mvg.sky.account.service.profile.ProfileService;
import com.mvg.sky.common.exception.RequestException;
import com.mvg.sky.common.response.SimpleResponseEntity;
import com.mvg.sky.repository.entity.ProfileEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "Profile API")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("/profiles")
    public ResponseEntity<?> getAllProfilesApi(@Nullable @RequestParam("accountId") List<String> accountIds,
                                               @Nullable @RequestParam("sort") List<String> sorts,
                                               @Nullable @RequestParam("offset") Integer offset,
                                               @Nullable @RequestParam("limit") Integer limit) {
        try {
            sorts = sorts == null ? List.of("id") : sorts;
            offset = offset == null ? 0 : offset;
            limit = limit == null ? Integer.MAX_VALUE : limit;

            return ResponseEntity.ok(profileService.getAllProfilesByAccountIds(accountIds, sorts, offset, limit));
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/profiles/{profileId}")
    public ResponseEntity<?> getProfilesApi(@PathVariable String profileId) {
        try {
            return ResponseEntity.ok(profileService.getProfileById(profileId));
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/profiles")
    public ResponseEntity<?> createProfileApi(@Valid @RequestBody ProfileCreationRequest profileCreationRequest) {
        try {
            ProfileEntity profileEntity = profileService.createProfile(profileCreationRequest);

            return ResponseEntity.created(URI.create("/api/profiles/" + profileEntity.getId())).body(profileEntity);
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/profiles/{profileId}")
    public ResponseEntity<?> putProfileApi(@PathVariable String profileId, @Valid @RequestBody ProfileModifyRequest profileModifyRequest) {
        try {
            return ResponseEntity.ok(profileService.updateFullProfile(profileId, profileModifyRequest));
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/profiles/{profileId}")
    public ResponseEntity<?> patchProfileApi(@PathVariable String profileId, @Valid @RequestBody ProfileModifyRequest profileModifyRequest) {
        try {
            return ResponseEntity.ok(profileService.updatePartialProfile(profileId, profileModifyRequest));
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/profiles/{profileId}")
    public ResponseEntity<?> deleteProfileApi(@PathVariable String profileId) {
        try {
            int num = profileService.deleteProfileById(profileId);

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

    @PatchMapping(value = "/profiles/avatar/{profileId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadAvatarApi(@PathVariable String profileId, @RequestPart MultipartFile avatar) {
        try {
            return ResponseEntity.ok(profileService.uploadProfileAvatar(profileId, avatar));
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
