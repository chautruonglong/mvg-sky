package com.mvg.sky.chat.service.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-account")
public interface AuthService {
    @GetMapping("/api/sessions/{token}/validate")
    ResponseEntity<?> validate(@PathVariable String token);
}
