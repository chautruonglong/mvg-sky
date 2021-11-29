package com.mvg.sky.configuration.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigurationController {
    @GetMapping("/configurations")
    public ResponseEntity<?> configurations() {
        return ResponseEntity.ok("configurations");
    }
}
