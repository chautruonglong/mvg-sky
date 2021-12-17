package com.mvg.sky.chat.service.message;

import com.mvg.sky.chat.dto.payload.MessageSendingPayload;
import com.mvg.sky.common.enumeration.MessageEnumeration;
import com.mvg.sky.repository.entity.MessageEntity;
import java.util.Collection;
import java.util.List;

public interface MessageService {
    MessageEntity saveNewMessageRealtime(String roomId, MessageSendingPayload messageSendingPayload);

    Collection<MessageEntity> getAllMessages(List<String> roomIds,
                                             List<MessageEnumeration> types,
                                             List<String> sorts,
                                             Integer offset,
                                             Integer limit);

    Integer deleteMessageById(String messageId);
}
