package com.mvg.sky.common.exception.handler;

import com.mvg.sky.common.exception.RequestException;
import com.mvg.sky.common.exception.entity.ResponseExceptionEntity;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@EnableWebMvc
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final HttpServletRequest httpServletRequest;

    @Value("${spring.application.name}")
    private String service;

    @Autowired
    public GlobalExceptionHandler(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @ExceptionHandler(RequestException.class)
    public ResponseEntity<Object> handleParentRequestException(RequestException requestException) {
        ResponseExceptionEntity responseExceptionEntity = ResponseExceptionEntity.builder()
            .api(httpServletRequest.getRequestURI())
            .method(httpServletRequest.getMethod())
            .port(httpServletRequest.getServerPort())
            .service(service)
            .message(requestException.getMessage())
            .error(requestException.getHttpStatus().name())
            .code(requestException.getHttpStatus().value())
            .build();

        return new ResponseEntity<>(responseExceptionEntity, requestException.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception exception) {
        ResponseExceptionEntity responseExceptionEntity = ResponseExceptionEntity.builder()
            .api(httpServletRequest.getRequestURI())
            .method(httpServletRequest.getMethod())
            .port(httpServletRequest.getServerPort())
            .service(service)
            .message(exception.getMessage())
            .error(HttpStatus.INTERNAL_SERVER_ERROR.name())
            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .build();

        return new ResponseEntity<>(responseExceptionEntity, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception exception, @Nullable Object body, @NonNull HttpHeaders httpHeaders, @NonNull HttpStatus httpStatus, @NonNull WebRequest webRequest) {
        ResponseExceptionEntity responseExceptionEntity = ResponseExceptionEntity.builder()
            .api(httpServletRequest.getRequestURI())
            .method(httpServletRequest.getMethod())
            .port(httpServletRequest.getServerPort())
            .service(service)
            .message(exception.getMessage())
            .error(httpStatus.name())
            .code(httpStatus.value())
            .build();

        return new ResponseEntity<>(responseExceptionEntity, httpStatus);
    }
}
