package com.mvg.sky.imap.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImapController {
    @GetMapping("/imap")
    public ResponseEntity<?> imap() {
        return ResponseEntity.ok("imap");
    }
}
