package com.mvg.sky.repository.dto.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mvg.sky.repository.entity.ContactEntity;
import com.mvg.sky.repository.entity.ProfileEntity;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
import lombok.Data;

@Data
public class ProfileContactDto {
    private UUID id;

    private UUID contactId;

    private UUID accountId;

    private String firstName;

    private String lastName;

    private String title;

    private String mobile;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String location;

    private String avatar;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date updatedAt;

    private Boolean isDeleted;

    public ProfileContactDto(ProfileEntity profileEntity, ContactEntity contactEntity) {
        this.id = profileEntity.getId();
        this.contactId = contactEntity != null ? contactEntity.getId() : null;
        this.accountId = profileEntity.getAccountId();
        this.firstName = profileEntity.getFirstName();
        this.lastName = profileEntity.getLastName();
        this.title = profileEntity.getTitle();
        this.mobile = profileEntity.getMobile();
        this.birthday = profileEntity.getBirthday();
        this.location = profileEntity.getLocation();
        this.avatar = profileEntity.getAvatar();
        this.createdAt = profileEntity.getCreatedAt();
        this.updatedAt = profileEntity.getUpdatedAt();
        this.isDeleted = profileEntity.getIsDeleted();
    }
}
