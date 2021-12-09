package com.mvg.sky.chat.dto;

import com.mvg.sky.common.enumeration.RoomEnumeration;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RoomCreationRequest {
    @NotNull
    private String name;

    private String description;

    private MultipartFile avatar;

    @NotNull
    private RoomEnumeration type;
}
