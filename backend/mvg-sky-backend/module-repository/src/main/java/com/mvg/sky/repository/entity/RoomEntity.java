package com.mvg.sky.repository.entity;

import com.google.common.net.UrlEscapers;
import com.mvg.sky.common.enumeration.RoomEnumeration;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "rooms")
public class RoomEntity extends BaseEntity {
    @Column(name = "name", columnDefinition = "text")
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "avatar", columnDefinition = "text")
    private String avatar;

    @Column(name = "type", columnDefinition = "text")
    @Enumerated(EnumType.STRING)
    private RoomEnumeration type;

    public String getAvatar() {
        if(avatar != null) {
            return UrlEscapers.urlFragmentEscaper().escape("/api/chats-resources/avatar/" + avatar);
        }
        return null;
    }
}
