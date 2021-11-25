package com.mvg.sky.account.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvg.sky.common.exception.entity.ResponseExceptionEntity;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Value("${spring.application.name}")
    private String service;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException accessDeniedException) throws IOException {
        ResponseExceptionEntity responseExceptionEntity = ResponseExceptionEntity.builder()
            .api(httpServletRequest.getRequestURI())
            .method(httpServletRequest.getMethod())
            .port(httpServletRequest.getServerPort())
            .service(service)
            .message("Forbidden")
            .error(HttpStatus.FORBIDDEN.name())
            .code(HttpStatus.FORBIDDEN.value())
            .build();

        String rawJson = new ObjectMapper().writeValueAsString(responseExceptionEntity);

        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
        httpServletResponse.getWriter().write(rawJson);
    }
}
