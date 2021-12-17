package com.mvg.sky.chat.controller;

import com.mvg.sky.chat.dto.request.RoomCreationRequest;
import com.mvg.sky.chat.dto.request.RoomUpdateRequest;
import com.mvg.sky.chat.service.room.RoomService;
import com.mvg.sky.common.enumeration.RoomEnumeration;
import com.mvg.sky.common.exception.RequestException;
import com.mvg.sky.common.response.SimpleResponseEntity;
import com.mvg.sky.repository.entity.RoomEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

            Collection<RoomEntity> roomEntities = roomService.getAllRooms(accountIds, sorts, offset, limit);

            roomEntities.forEach(roomEntity -> {
                if(roomEntity.getAvatar() != null) {
                    roomEntity.setAvatar("/api/chats-resources/avatar/" + roomEntity.getAvatar());
                }
            });

            return ResponseEntity.ok(roomEntities);
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/rooms")
    public ResponseEntity<?> createRoomApi(@Valid @RequestBody RoomCreationRequest roomCreationRequest) {
        try {
            if(roomCreationRequest.getType() == RoomEnumeration.P2P && (roomCreationRequest.getAccountIds() == null || roomCreationRequest.getAccountIds().size() != 2)) {
                throw new RuntimeException("Room type P2P required two accountIds");
            }

            RoomEntity roomEntity = roomService.createRoom(roomCreationRequest);

            if(roomEntity.getAvatar() != null) {
                roomEntity.setAvatar("/api/chats-resources/avatar/" + roomEntity.getAvatar());
            }

            return ResponseEntity.created(URI.create("/api/rooms/" + roomEntity.getId())).body(roomEntity);
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<?> deleteRoomApi(@PathVariable String roomId) {
        try {
            int num = roomService.deleteRoom(roomId);

            return ResponseEntity.ok(
                SimpleResponseEntity.builder()
                    .message("Deleted room successfully")
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

    @PatchMapping("/rooms/{roomId}")
    public ResponseEntity<?> patchRoomApi(@PathVariable String roomId, @Valid @RequestBody RoomUpdateRequest roomUpdateRequest) {
        try {
            RoomEntity roomEntity = roomService.updatePartialRoom(roomId, roomUpdateRequest);

            if(roomEntity.getAvatar() != null) {
                roomEntity.setAvatar("/api/chats-resources/avatar/" + roomEntity.getAvatar());
            }

            return ResponseEntity.ok(roomEntity);
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/rooms/{roomId}")
    public ResponseEntity<?> putRoomApi(@PathVariable String roomId, @Valid @RequestBody RoomUpdateRequest roomUpdateRequest) {
        try {
            RoomEntity roomEntity = roomService.updateRoom(roomId, roomUpdateRequest);

            if(roomEntity.getAvatar() != null) {
                roomEntity.setAvatar("/api/chats-resources/avatar/" + roomEntity.getAvatar());
            }

            return ResponseEntity.ok(roomEntity);
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/rooms/avatar/{roomId}")
    public ResponseEntity<?> uploadRoomAvatarApi(@PathVariable String roomId, @RequestPart MultipartFile avatar) {
        try {
            RoomEntity roomEntity = roomService.uploadRoomAvatar(roomId, avatar);

            if(roomEntity.getAvatar() != null) {
                roomEntity.setAvatar("/api/chats-resources/avatar/" + roomEntity.getAvatar());
            }

            return ResponseEntity.ok(roomEntity);
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
