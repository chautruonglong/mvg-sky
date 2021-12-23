package com.mvg.sky.repository.dto.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mvg.sky.repository.entity.ProfileEntity;
import com.mvg.sky.repository.entity.RoomAccountEntity;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
import lombok.Data;

@Data
public class MemberDto {
    private UUID id;

    private UUID roomId;

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

    public MemberDto(RoomAccountEntity roomAccountEntity, ProfileEntity profileEntity) {
        this.id = roomAccountEntity.getId();
        this.roomId = roomAccountEntity.getRoomId();
        this.accountId = roomAccountEntity.getAccountId();
        this.firstName = profileEntity.getFirstName();
        this.lastName = profileEntity.getLastName();
        this.title = profileEntity.getTitle();
        this.mobile = profileEntity.getMobile();
        this.birthday = profileEntity.getBirthday();
        this.location = profileEntity.getLocation();
        this.avatar = profileEntity.getAvatar();
        this.createdAt = roomAccountEntity.getCreatedAt();
        this.updatedAt = roomAccountEntity.getUpdatedAt();
        this.isDeleted = roomAccountEntity.getIsDeleted();
    }
}
