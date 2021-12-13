package com.mvg.sky.chat.service.message;

import com.mvg.sky.chat.dto.payload.MessageSendingPayload;
import com.mvg.sky.common.enumeration.MessageEnumeration;
import com.mvg.sky.repository.MessageRepository;
import com.mvg.sky.repository.entity.MessageEntity;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Override
    public MessageEntity saveNewMessageRealtime(String roomId, MessageSendingPayload messageSendingPayload) {
        MessageEntity messageEntity = MessageEntity
            .builder()
            .accountId(UUID.fromString(messageSendingPayload.getAccountId()))
            .roomId(UUID.fromString(roomId))
            .threadId(messageSendingPayload.getThreadId() != null ? UUID.fromString(messageSendingPayload.getThreadId()) : null)
            .content(messageSendingPayload.getContent())
            .type(messageSendingPayload.getType())
            .isInSchedule(messageSendingPayload.getDelay() > 0)
            .build();

        messageEntity = messageRepository.save(messageEntity);

        log.info("save new message {}", messageEntity);
        return messageEntity;
    }

    @Override
    public Collection<MessageEntity> getAllMessages(List<String> roomIds, List<MessageEnumeration> types, List<String> sorts, Integer offset, Integer limit) {
        Sort sort = Sort.by(Sort.Direction.DESC, sorts.toArray(String[]::new));
        Pageable pageable = PageRequest.of(offset, limit, sort);
        Collection<MessageEntity> messageEntities;

        if(roomIds == null) {
            if(types == null) {
                messageEntities = messageRepository.findAllByIsDeletedFalseAndIsInScheduleFalse(pageable);
            }
            else {
                messageEntities = messageRepository.findAllByTypeInAndIsDeletedFalseAndIsInScheduleFalse(types, pageable);
            }
        }
        else {
            if(types == null) {
                messageEntities = messageRepository.findAllByRoomIdInAndIsDeletedFalseAndIsInScheduleFalse(
                    roomIds.stream().map(UUID::fromString).collect(Collectors.toList()),
                    pageable
                );
            }
            else {
                messageEntities = messageRepository.findAllByRoomIdInAndTypeInAndIsDeletedFalseAndIsInScheduleFalse(
                    roomIds.stream().map(UUID::fromString).collect(Collectors.toList()),
                    types,
                    pageable
                );
            }
        }

        log.info("find {} messages", messageEntities == null ? 0 : messageEntities.size());
        return messageEntities;
    }

    @Override
    public Integer deleteMessageById(String messageId) {
        int num = messageRepository.deleteByIdAndIsDeletedFalse(UUID.fromString(messageId));

        log.info("delete message successfully, {} records changed", num);
        return num;
    }
}
