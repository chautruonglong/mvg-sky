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
    String error;
    Integer code;
    String message;
    String api;
    ZonedDateTime timestamp;
}
