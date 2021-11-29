package com.mvg.sky.smtp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmtpController {
    @GetMapping("/smtp")
    public ResponseEntity<?> smtp() {
        return ResponseEntity.ok("smtp");
    }
}
