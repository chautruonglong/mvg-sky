package com.mvg.sky.common.exception.entity;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class ResponseExceptionEntity {
    private String api;

    private String method;

    private Integer port;

    private String service;

    private String message;

    private String error;

    private Integer code;

    @Builder.Default
    private String timestamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyy-MM-dd hh:mm:ss a z Z"));
}
