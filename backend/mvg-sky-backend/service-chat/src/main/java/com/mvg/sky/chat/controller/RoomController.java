package com.mvg.sky.chat.controller;

import com.mvg.sky.chat.dto.RoomCreationRequest;
import com.mvg.sky.chat.service.room.RoomService;
import com.mvg.sky.common.exception.RequestException;
import com.mvg.sky.repository.entity.RoomEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "Room API")
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/rooms")
    public ResponseEntity<?> getAllRoomsApi(@Nullable @RequestParam("accountId") List<String> accountIds,
                                            @Nullable @RequestParam("sort") List<String> sorts,
                                            @Nullable @RequestParam("offset") Integer offset,
                                            @Nullable @RequestParam("limit") Integer limit) {
        try {
            sorts = sorts == null ? List.of("id") : sorts;
            offset = offset == null ? 0 : offset;
            limit = limit == null ? Integer.MAX_VALUE : limit;

            return ResponseEntity.ok(roomService.getAllRooms(accountIds, sorts, offset, limit));
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/rooms")
    public ResponseEntity<?> createRoomApi(@Valid @ModelAttribute RoomCreationRequest roomCreationRequest) {
        try {
            RoomEntity roomEntity = roomService.createRoom(roomCreationRequest);
            return ResponseEntity.created(URI.create("/api/rooms/" + roomEntity.getId())).body(roomEntity);
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
