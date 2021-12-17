package com.mvg.sky.chat.controller;

import com.mvg.sky.chat.dto.payload.MessageDeletingPayload;
import com.mvg.sky.chat.dto.payload.MessageDestroyingPayload;
import com.mvg.sky.chat.dto.payload.MessageSendingPayload;
import com.mvg.sky.chat.dto.payload.OutputPayload;
import com.mvg.sky.chat.enumeration.PayloadEnumeration;
import com.mvg.sky.chat.scheduler.MessagingScheduler;
import com.mvg.sky.chat.service.message.MessageService;
import com.mvg.sky.repository.entity.MessageEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import java.util.UUID;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "Chat Realtime API")
public class ChatController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;
    private final MessagingScheduler messagingScheduler;

    @MessageMapping("/send-message/{roomId}")
    public void sendMessage(@DestinationVariable String roomId, @Valid @Payload MessageSendingPayload messageSendingPayload) {
        MessageEntity messageEntity = messageService.saveNewMessageRealtime(roomId, messageSendingPayload);

        if(messageSendingPayload.getDelay() > 0) {
            messagingScheduler.schedule(messageEntity, messageSendingPayload.getDelay());
        }
        else {
            OutputPayload outputPayload = new OutputPayload();
            outputPayload.setCommand(PayloadEnumeration.MESSAGE);
            outputPayload.setData(messageEntity);

            simpMessagingTemplate.convertAndSend("/room/" + roomId, outputPayload);
        }
    }

    @MessageMapping("/destroy-scheduled-message/{roomId}")
    public void destroyScheduler(@DestinationVariable String roomId, @Valid @Payload MessageDestroyingPayload messageDestroyingPayload) {
        messagingScheduler.destroyScheduler(
            UUID.fromString(roomId),
            UUID.fromString(messageDestroyingPayload.getMessageId())
       );
    }

    @MessageMapping("/delete-message/{roomId}")
    public void deleteMessage(@DestinationVariable String roomId, @Valid @Payload MessageDeletingPayload messageDeletingPayload) {
        int num = messageService.deleteMessageById(messageDeletingPayload.getMessageId());
        OutputPayload outputPayload = new OutputPayload();
        outputPayload.setCommand(PayloadEnumeration.DELETE);
        outputPayload.setData(Map.of("messageId", messageDeletingPayload.getMessageId(), "num", num));

        simpMessagingTemplate.convertAndSend("/room/" + roomId, outputPayload);
    }
}
