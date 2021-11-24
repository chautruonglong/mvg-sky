package com.mvg.sky.common.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RequestExceptionHandler {
    @ExceptionHandler({RequestException.class})
    public ResponseEntity<Object> handler(RequestException requestException) {
        ResponseExceptionEntity responseExceptionEntity= ResponseExceptionEntity.builder()
            .error(requestException.getError())
            .code(requestException.getCode())
            .message(requestException.getMessage())
            .api(requestException.getApi())
            .timestamp(ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")))
            .build();

        return new ResponseEntity<>(responseExceptionEntity, HttpStatus.valueOf(requestException.getCode()));
    }
}
