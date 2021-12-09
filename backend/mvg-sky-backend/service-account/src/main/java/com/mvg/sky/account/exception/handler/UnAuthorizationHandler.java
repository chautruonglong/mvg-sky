package com.mvg.sky.account.exception.handler;

import com.mvg.sky.account.util.response.ResponseExceptionSender;
import com.mvg.sky.common.exception.entity.ResponseExceptionEntity;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UnAuthorizationHandler implements AuthenticationEntryPoint {
    @Value("${spring.application.name}")
    private String service;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException authenticationException) throws IOException {
        ResponseExceptionSender.send(httpServletRequest, httpServletResponse, authenticationException, service);
    }

    @ExceptionHandler({AuthenticationException.class, UsernameNotFoundException.class})
    public ResponseEntity<Object> handle(Exception exception, HttpServletRequest httpServletRequest) {
        ResponseExceptionEntity responseExceptionEntity = ResponseExceptionEntity.builder()
            .api(httpServletRequest.getRequestURI())
            .method(httpServletRequest.getMethod())
            .port(httpServletRequest.getServerPort())
            .service(service)
            .message(exception.getMessage())
            .error(HttpStatus.UNAUTHORIZED.name())
            .code(HttpStatus.UNAUTHORIZED.value())
            .build();

        return new ResponseEntity<>(responseExceptionEntity, HttpStatus.UNAUTHORIZED);
    }
}
