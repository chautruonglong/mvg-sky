package com.mvg.sky.repository.entity;

import com.google.common.net.UrlEscapers;
import com.mvg.sky.common.enumeration.MessageEnumeration;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Table(
    name = "messages",
    indexes = {
        @Index(columnList = "accountId"),
        @Index(columnList = "roomId"),
        @Index(columnList = "threadId"),
        @Index(columnList = "content"),
        @Index(columnList = "type"),
        @Index(columnList = "isDeleted"),
        @Index(columnList = "isInSchedule"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "UpdatedAt"),
    }
)
public class MessageEntity extends BaseEntity {
    @Column(name = "accountId", nullable = false, columnDefinition = "uuid")
    private UUID accountId;

    @Column(name = "roomId", nullable = false, columnDefinition = "uuid")
    private UUID roomId;

    @Column(name = "threadId", columnDefinition = "uuid")
    private UUID threadId;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "type", columnDefinition = "text")
    @Enumerated(EnumType.STRING)
    private MessageEnumeration type;

    @Builder.Default
    @Column(name = "isInSchedule", columnDefinition = "boolean default false")
    private Boolean isInSchedule = false;

    public String getContent() {
        if(type == MessageEnumeration.MEDIA && content != null) {
            return UrlEscapers.urlFragmentEscaper().escape("/api/chats-resources/media/" + content);
        }
        return content;
    }
}
