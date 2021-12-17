package com.mvg.sky.account.util.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvg.sky.common.exception.entity.ResponseExceptionEntity;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ResponseExceptionSender {
    public static void send(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Exception exception, String service) throws IOException {
        ResponseExceptionEntity responseExceptionEntity = ResponseExceptionEntity.builder()
            .api(httpServletRequest.getRequestURI())
            .method(httpServletRequest.getMethod())
            .port(httpServletRequest.getServerPort())
            .service(service)
            .message(exception.getMessage())
            .error(HttpStatus.UNAUTHORIZED.name())
            .code(HttpStatus.UNAUTHORIZED.value())
            .build();

        String rawJson = new ObjectMapper().writeValueAsString(responseExceptionEntity);

        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.getWriter().write(rawJson);
    }
}
