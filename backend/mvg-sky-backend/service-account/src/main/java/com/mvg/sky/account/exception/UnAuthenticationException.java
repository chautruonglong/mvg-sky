package com.mvg.sky.account.exception;

import com.mvg.sky.common.exception.RequestException;
import org.springframework.http.HttpStatus;

public class UnAuthenticationException extends RequestException {
    public UnAuthenticationException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
