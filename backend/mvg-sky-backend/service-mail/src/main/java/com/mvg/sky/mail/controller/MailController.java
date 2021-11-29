package com.mvg.sky.mail.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {
    @GetMapping("/mails")
    public ResponseEntity<?> mails() {
        return ResponseEntity.ok("mails");
    }
}
