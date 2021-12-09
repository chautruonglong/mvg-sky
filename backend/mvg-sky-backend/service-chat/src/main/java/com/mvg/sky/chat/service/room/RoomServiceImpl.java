package com.mvg.sky.chat.service.room;

import com.mvg.sky.chat.dto.RoomCreationRequest;
import com.mvg.sky.common.exception.RequestException;
import com.mvg.sky.common.util.file.FileUtil;
import com.mvg.sky.repository.RoomRepository;
import com.mvg.sky.repository.entity.RoomEntity;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    @Override
    public Collection<RoomEntity> getAllRooms(List<String> accountIds, List<String> sorts, Integer offset, Integer limit) {
        Sort sort = Sort.by(Sort.Direction.ASC, sorts.toArray(String[]::new));
        Pageable pageable = PageRequest.of(offset, limit, sort);
        Collection<RoomEntity> roomEntities;

        if(accountIds == null) {
            roomEntities = roomRepository.findAllByIsDeletedFalse(pageable);
        }
        else {
            roomEntities = roomRepository.findAllByAccountIdInAndIsDeletedFalse(
                accountIds.stream().map(UUID::fromString).collect(Collectors.toList()),
                pageable
            );
        }

        log.info("find {} room entities", roomEntities == null ? 0 : roomEntities.size());
        return roomEntities;
    }

    @Override
    public RoomEntity createRoom(RoomCreationRequest roomCreationRequest) throws IOException {
        String fileName = roomCreationRequest.getAvatar().getOriginalFilename();
        assert fileName != null;
        fileName = FileUtil.changeFileName(fileName, UUID.randomUUID().toString());

        if(!FileUtil.isImageFile(fileName)) {
            throw new RequestException("Avatar file is not an image", HttpStatus.BAD_REQUEST);
        }

        RoomEntity roomEntity = RoomEntity.builder()
            .name(roomCreationRequest.getName())
            .description(roomCreationRequest.getDescription())
            .type(roomCreationRequest.getType())
            .avatar(fileName)
            .build();

        roomEntity = roomRepository.save(roomEntity);

        roomCreationRequest.getAvatar().transferTo(new File("upload/" + fileName));

        log.info("save new room {}", roomEntity);
        return null;
    }
}
