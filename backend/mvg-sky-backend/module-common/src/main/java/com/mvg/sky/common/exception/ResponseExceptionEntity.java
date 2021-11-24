package com.mvg.sky.common.exception;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class ResponseExceptionEntity {
    private String api;
    private String method;
    private String error;
    private String message;
    private Integer port;
    private Integer code;
    private ZonedDateTime timestamp;
}
