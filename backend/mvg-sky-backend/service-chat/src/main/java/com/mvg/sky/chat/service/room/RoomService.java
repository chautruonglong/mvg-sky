package com.mvg.sky.chat.service.room;

import com.mvg.sky.chat.dto.request.RoomCreationRequest;
import com.mvg.sky.chat.dto.request.RoomUpdateRequest;
import com.mvg.sky.repository.dto.query.RoomMessageDto;
import com.mvg.sky.repository.entity.RoomEntity;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface RoomService {
    Collection<RoomMessageDto> getAllRooms(List<String> accountIds,
                                           List<String> sorts,
                                           Integer offset,
                                           Integer limit);

    RoomEntity createRoom(RoomCreationRequest roomCreationRequest) throws IOException;

    Integer deleteRoom(String roomId);

    RoomEntity updatePartialRoom(String roomId, RoomUpdateRequest roomUpdateRequest) throws IOException;

    RoomEntity updateRoom(String roomId, RoomUpdateRequest roomUpdateRequest) throws IOException;

    RoomEntity uploadRoomAvatar(String roomId, MultipartFile avatar) throws IOException;
}
