package com.mvg.sky.common.exception;

import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RequestException extends RuntimeException {
    private final String api;
    private final String method;
    private final String error;
    private final Integer port;
    private final Integer code;

    public RequestException(String message, HttpServletRequest httpServletRequest, HttpStatus httpStatus) {
        super(message);
        api = httpServletRequest.getRequestURI();
        method = httpServletRequest.getMethod();
        error = httpStatus.name();
        port = httpServletRequest.getServerPort();
        code = httpStatus.value();
    }
}
