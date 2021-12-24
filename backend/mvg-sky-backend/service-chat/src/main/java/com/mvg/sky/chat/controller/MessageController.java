package com.mvg.sky.chat.controller;

import com.mvg.sky.chat.dto.request.MediaMessageRequest;
import com.mvg.sky.chat.service.message.MessageService;
import com.mvg.sky.common.enumeration.MessageEnumeration;
import com.mvg.sky.common.exception.RequestException;
import com.mvg.sky.common.response.SimpleResponseEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "Message API")
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/messages")
    public ResponseEntity<?> getAllMessagesApi(@Nullable @RequestParam("roomId") List<String> roomIds,
                                               @Nullable @RequestParam("type") List<MessageEnumeration> types,
                                               @Nullable @RequestParam("sort") List<String> sorts,
                                               @Nullable @RequestParam("offset") Integer offset,
                                               @Nullable @RequestParam("limit") Integer limit) {
        try {
            sorts = sorts == null ? List.of("createdAt") : sorts;
            offset = offset == null ? 0 : offset;
            limit = limit == null ? Integer.MAX_VALUE : limit;

            return ResponseEntity.ok(messageService.getAllMessages(roomIds, types, sorts, offset, limit));
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessageApi(@PathVariable String messageId) {
        try {
            int num = messageService.deleteMessageById(messageId);

            return ResponseEntity.ok(
                SimpleResponseEntity.builder()
                    .message("Deleted message successfully")
                    .recordsChanged(num)
                    .status(HttpStatus.OK.name())
                    .code(HttpStatus.OK.value())
                    .build()
            );
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/messages/send-media/{roomId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> sendMediaMessageApi(@PathVariable String roomId, @Valid @ModelAttribute MediaMessageRequest mediaMessageRequest) {
        try {
            if(mediaMessageRequest.getType() != MessageEnumeration.MEDIA) {
                throw new RuntimeException("Message type required is media");
            }

            return ResponseEntity.ok(messageService.sendMediaMessage(roomId, mediaMessageRequest));
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
