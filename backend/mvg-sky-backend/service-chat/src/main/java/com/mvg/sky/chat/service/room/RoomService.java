package com.mvg.sky.chat.service.room;

import com.mvg.sky.chat.dto.RoomCreationRequest;
import com.mvg.sky.repository.entity.RoomEntity;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface RoomService {
    Collection<RoomEntity> getAllRooms(List<String> accountIds,
                                       List<String> sorts,
                                       Integer offset,
                                       Integer limit);

    RoomEntity createRoom(RoomCreationRequest roomCreationRequest) throws IOException;
}
