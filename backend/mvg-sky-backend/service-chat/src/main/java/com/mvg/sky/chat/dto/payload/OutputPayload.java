package com.mvg.sky.chat.dto.payload;

import com.mvg.sky.chat.enumeration.PayloadEnumeration;
import lombok.Data;

@Data
public class OutputPayload {
    private PayloadEnumeration command;

    private Object data;
}
