package com.mvg.sky.chat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    @GetMapping("chats")
    public ResponseEntity<?> chats() {
        return ResponseEntity.ok("Hello");
    }
}
