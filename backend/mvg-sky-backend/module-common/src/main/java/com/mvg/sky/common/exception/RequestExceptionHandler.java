package com.mvg.sky.common.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RequestExceptionHandler {
    @ExceptionHandler({RequestException.class})
    public static ResponseEntity<ResponseExceptionEntity> handle(RequestException requestException) {
        ResponseExceptionEntity responseExceptionEntity= ResponseExceptionEntity.builder()
            .api(requestException.getApi())
            .method(requestException.getMethod())
            .error(requestException.getError())
            .message(requestException.getMessage())
            .port(requestException.getPort())
            .code(requestException.getCode())
            .timestamp(ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")))
            .build();

        return new ResponseEntity<>(responseExceptionEntity, HttpStatus.valueOf(requestException.getCode()));
    }

}
