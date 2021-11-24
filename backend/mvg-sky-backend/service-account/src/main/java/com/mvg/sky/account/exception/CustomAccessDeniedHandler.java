package com.mvg.sky.account.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvg.sky.common.exception.RequestException;
import com.mvg.sky.common.exception.RequestExceptionHandler;
import com.mvg.sky.common.exception.ResponseExceptionEntity;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        RequestException requestException = new RequestException("Forbidden", request, HttpStatus.FORBIDDEN);
        ResponseEntity<ResponseExceptionEntity> responseEntity = RequestExceptionHandler.handle(requestException);
        String rawJson = objectMapper.writeValueAsString(responseEntity.getBody());

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(rawJson);
    }
}
