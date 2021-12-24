package com.mvg.sky.james.dto;

import com.mvg.sky.james.entity.JamesMail;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JamesMailDto {
    private JamesMail jamesMail;

    private UUID accountId;
}
