package com.mvg.sky.chat.service.message;

import com.mvg.sky.chat.dto.payload.MessageSendingPayload;
import com.mvg.sky.chat.dto.payload.OutputPayload;
import com.mvg.sky.chat.dto.request.MediaMessageRequest;
import com.mvg.sky.chat.enumeration.PayloadEnumeration;
import com.mvg.sky.chat.scheduler.MessagingScheduler;
import com.mvg.sky.common.enumeration.MessageEnumeration;
import com.mvg.sky.repository.MessageRepository;
import com.mvg.sky.repository.dto.query.MessageDto;
import com.mvg.sky.repository.entity.MessageEntity;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    @NonNull
    private final MessageRepository messageRepository;

    @NonNull
    private final MessagingScheduler messagingScheduler;

    @NonNull
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Value("${com.mvg.sky.service-chat.external-resource}")
    private String externalResources;

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
    public Collection<MessageDto> getAllMessages(List<String> roomIds, List<MessageEnumeration> types, List<String> sorts, Integer offset, Integer limit) {
        Sort sort = Sort.by(Sort.Direction.DESC, sorts.toArray(String[]::new));
        Pageable pageable = PageRequest.of(offset, limit, sort);

        List<UUID> roomUuids = roomIds != null ? roomIds.stream().map(UUID::fromString).toList() : null;
        Collection<MessageDto> messageEntities = messageRepository.findALlMessages(roomUuids, types, pageable);

        log.info("find {} messages", messageEntities == null ? 0 : messageEntities.size());
        return messageEntities;
    }

    @Override
    public Integer deleteMessageById(String messageId) {
        int num = messageRepository.deleteByIdAndIsDeletedFalse(UUID.fromString(messageId));

        log.info("delete message successfully, {} records changed", num);
        return num;
    }

    @Override
    public MessageEntity sendMediaMessage(String roomId, MediaMessageRequest mediaMessageRequest) throws IOException {
        MessageEntity messageEntity = MessageEntity.builder()
            .accountId(UUID.fromString(mediaMessageRequest.getAccountId()))
            .roomId(UUID.fromString(roomId))
            .threadId(mediaMessageRequest.getThreadId() != null ? UUID.fromString(mediaMessageRequest.getThreadId()) : null)
            .type(mediaMessageRequest.getType())
            .isInSchedule(mediaMessageRequest.getDelay() > 0)
            .build();

        String fileName = null;

        if(mediaMessageRequest.getContent() != null) {
            fileName = mediaMessageRequest.getContent().getOriginalFilename();
        }

        if(fileName != null && !fileName.equals("")) {
            String uuidPath = UUID.randomUUID().toString();
            fileName = uuidPath + "/" + fileName;

            String path = externalResources.substring("file:".length()) + "/chats-resources/media/";
            Files.createDirectories(Paths.get(path + uuidPath));
            mediaMessageRequest.getContent().transferTo(new File(path + fileName));
        }

        messageEntity.setContent(fileName);
        messageEntity = messageRepository.save(messageEntity);

        if(mediaMessageRequest.getDelay() > 0) {
            messagingScheduler.schedule(messageEntity, mediaMessageRequest.getDelay());
        }
        else {
            OutputPayload outputPayload = new OutputPayload();
            outputPayload.setCommand(PayloadEnumeration.MESSAGE);
            outputPayload.setData(messageEntity);

            simpMessagingTemplate.convertAndSend("/room/" + roomId, outputPayload);
        }

        return messageEntity;
    }
}
