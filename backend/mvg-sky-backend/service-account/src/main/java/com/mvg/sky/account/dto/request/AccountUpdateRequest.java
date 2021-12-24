package com.mvg.sky.account.dto.request;

import com.mvg.sky.common.enumeration.RoleEnumeration;
import com.mvg.sky.common.util.validator.EnumValidator;
import javax.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AccountUpdateRequest {
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character"
    )
    private String password;

    private Boolean isActive;

    @EnumValidator(enumClass = RoleEnumeration.class, allowNull = false)
    private RoleEnumeration[] roles;
}
