package com.mvg.sky.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SimpleResponseEntity {
    private String message;

    private String status;

    private Integer code;

    @Builder.Default
    private Integer recordsChanged = 0;
}
