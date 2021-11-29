package com.mvg.sky.account.dto.request;

import java.time.LocalDate;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProfileCreationRequest {
    @NotNull
    private UUID accountId;

    private String firstName;

    private String lastName;

    private String title;

    private String mobile;

    private LocalDate birthday;

    private String location;
}
