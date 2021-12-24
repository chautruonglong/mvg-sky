package com.mvg.sky.chat.service.message;

import com.mvg.sky.chat.dto.payload.MessageSendingPayload;
import com.mvg.sky.chat.dto.request.MediaMessageRequest;
import com.mvg.sky.common.enumeration.MessageEnumeration;
import com.mvg.sky.repository.dto.query.MessageDto;
import com.mvg.sky.repository.entity.MessageEntity;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface MessageService {
    MessageEntity saveNewMessageRealtime(String roomId, MessageSendingPayload messageSendingPayload);

    Collection<MessageDto> getAllMessages(List<String> roomIds,
                                          List<MessageEnumeration> types,
                                          List<String> sorts,
                                          Integer offset,
                                          Integer limit);

    Integer deleteMessageById(String messageId);

    MessageEntity sendMediaMessage(String roomId, MediaMessageRequest mediaMessageRequest) throws IOException;
}
