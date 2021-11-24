package com.mvg.sky.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class RequestException extends RuntimeException {
    @Getter
    private final String api;

    @Getter
    private final String error;

    @Getter
    private final Integer code;

    public RequestException(String message, String api, HttpStatus httpStatus) {
        super(message);
        this.api = api;
        this.error = httpStatus.name();
        this.code = httpStatus.value();
    }
}
