package com.mvg.sky.account.dto.request;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ProfileModifyRequest {
    private String firstName;

    private String lastName;

    private String title;

    private String mobile;

    private LocalDate birthday;

    private String location;
}
