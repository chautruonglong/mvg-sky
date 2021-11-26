package com.mvg.sky.account.controller;

import com.mvg.sky.account.service.profile.ProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Tag(name = "Profile API")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("/profiles")
    public ResponseEntity<?> getAllProfilesApi(@Nullable @RequestParam("domain") List<String> domains,
                                               @Nullable @RequestParam("sort") List<String> sorts,
                                               @Nullable @RequestParam("offset") Integer offset,
                                               @Nullable @RequestParam("limit") Integer limit) {
        return ResponseEntity.ok("ok");
    }
}
