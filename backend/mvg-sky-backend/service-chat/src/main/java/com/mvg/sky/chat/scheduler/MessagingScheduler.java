package com.mvg.sky.chat.scheduler;

import com.mvg.sky.chat.dto.payload.OutputPayload;
import com.mvg.sky.chat.enumeration.PayloadEnumeration;
import com.mvg.sky.repository.MessageRepository;
import com.mvg.sky.repository.entity.MessageEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessagingScheduler {
    private final ScheduledExecutorService scheduledExecutorService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageRepository messageRepository;
    private final Map<MessageEntity, ScheduledFuture<?>> futureTasks;

    public MessagingScheduler(SimpMessagingTemplate simpMessagingTemplate, MessageRepository messageRepository) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageRepository = messageRepository;

        scheduledExecutorService = Executors.newScheduledThreadPool(2);
        futureTasks = new HashMap<>();
    }

    public void schedule(MessageEntity messageEntity, long delay) {
        Runnable runnable = () -> {
            messageEntity.setIsInSchedule(false);
            messageRepository.save(messageEntity);

            OutputPayload outputPayload = new OutputPayload();
            outputPayload.setCommand(PayloadEnumeration.MESSAGE);
            outputPayload.setData(messageEntity);

            simpMessagingTemplate.convertAndSend("/room/" + messageEntity.getRoomId(), outputPayload);

            futureTasks.remove(messageEntity);
        };

        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.schedule(runnable, delay, TimeUnit.SECONDS);
        futureTasks.put(messageEntity, scheduledFuture);
    }

    public void destroyScheduler(UUID roomId, UUID messageId) {
        futureTasks.forEach((messageEntity, value) -> {
            if(messageEntity.getRoomId().equals(roomId) && messageEntity.getId().equals(messageId) && messageEntity.getIsInSchedule()) {
                value.cancel(true);

                messageEntity.setIsDeleted(true);
                messageRepository.save(messageEntity);
            }
        });
    }
}
