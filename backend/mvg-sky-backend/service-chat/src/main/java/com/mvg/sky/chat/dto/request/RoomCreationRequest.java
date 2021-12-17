package com.mvg.sky.chat.dto.request;

import com.mvg.sky.common.enumeration.RoomEnumeration;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoomCreationRequest {
    @NotNull
    private String name;

    private String description;

    @NotNull
    private RoomEnumeration type;

    private List<String> accountIds;
}
