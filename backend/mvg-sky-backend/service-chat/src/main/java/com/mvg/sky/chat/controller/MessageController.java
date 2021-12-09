package com.mvg.sky.chat.controller;

import com.mvg.sky.chat.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MessageController {
    @MessageMapping("/chat.send")
    @SendTo("rooms/public")
    public MessageDto sendMessage(@Payload MessageDto messageDto) {
        log.info("{}", messageDto);
        return messageDto;
    }
}
