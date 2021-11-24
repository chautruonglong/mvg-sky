package com.mvg.sky.account.controller;

import com.mvg.sky.common.exception.RequestException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AccountController {
    @GetMapping("login")
    public String login(HttpServletRequest httpServletRequest) throws RequestException {
        try {
            throw new RuntimeException("ngu");
        } catch(Exception e) {
            throw new RequestException(e.getMessage(), httpServletRequest.getRequestURI(), HttpStatus.BAD_REQUEST);
        }
    }
}
