package com.mvg.sky.mail.filter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ACCOUNT-SERVICE")
public interface SessionConsumer {
    @GetMapping("/sessions/{token}/validate")
    ResponseEntity<?> validateToken(@PathVariable String token);
}
