package com.mvg.sky.chat.service.room;

import com.mvg.sky.chat.dto.request.RoomCreationRequest;
import com.mvg.sky.chat.dto.request.RoomUpdateRequest;
import com.mvg.sky.chat.util.mapper.MapperUtil;
import com.mvg.sky.common.exception.RequestException;
import com.mvg.sky.common.util.file.FileUtil;
import com.mvg.sky.repository.RoomAccountRepository;
import com.mvg.sky.repository.RoomRepository;
import com.mvg.sky.repository.dto.query.RoomMessageDto;
import com.mvg.sky.repository.entity.RoomAccountEntity;
import com.mvg.sky.repository.entity.RoomEntity;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    @NonNull
    private final RoomRepository roomRepository;

    @NonNull
    private final RoomAccountRepository roomAccountRepository;

    @NonNull
    private final MapperUtil mapperUtil;

    @Value("${com.mvg.sky.service-chat.external-resource}")
    private String externalResources;

    @Override
    public Collection<RoomMessageDto> getAllRooms(List<String> accountIds, List<String> sorts, Integer offset, Integer limit) {
        Sort sort = Sort.by(Sort.Direction.ASC, sorts.toArray(String[]::new));
        Pageable pageable = PageRequest.of(offset, limit, sort);

        List<UUID> accountUuids = accountIds != null ? accountIds.stream().map(UUID::fromString).toList() : null;
        Collection<RoomMessageDto> roomEntities = roomRepository.findAllRooms(accountUuids, pageable);

        log.info("find {} room entities", roomEntities == null ? 0 : roomEntities.size());
        return roomEntities;
    }

    @Override
    public RoomEntity createRoom(RoomCreationRequest roomCreationRequest) throws IOException {
        RoomEntity roomEntity = RoomEntity.builder()
            .name(roomCreationRequest.getName())
            .description(roomCreationRequest.getDescription())
            .type(roomCreationRequest.getType())
            .build();

        roomEntity = roomRepository.save(roomEntity);

        if(roomCreationRequest.getAccountIds() != null) {
            final RoomEntity finalRoomEntity = roomEntity;
            roomCreationRequest.getAccountIds().forEach(accountId -> {
                roomAccountRepository.save(
                    RoomAccountEntity.builder()
                        .roomId(finalRoomEntity.getId())
                        .accountId(UUID.fromString(accountId))
                        .build()
                );
            });
        }

        log.info("save new room {}", roomEntity);
        return roomEntity;
    }

    @Override
    public Integer deleteRoom(String roomId) {
        int num = roomRepository.deleteByIdAndIsDeletedFalse(UUID.fromString(roomId));

        log.info("delete room {} successfully, {} records updated", roomId, num);
        return num;
    }

    @Override
    public RoomEntity updatePartialRoom(String roomId, RoomUpdateRequest roomUpdateRequest) throws IOException {
        RoomEntity roomEntity = roomRepository.findById(UUID.fromString(roomId))
            .orElseThrow(() -> new RuntimeException("room not found"));

        mapperUtil.updatePartialRoomFromDto(roomUpdateRequest, roomEntity);
        roomEntity = roomRepository.save(roomEntity);

        log.info("updated room {}", roomEntity);
        return roomEntity;
    }

    @Override
    public RoomEntity updateRoom(String roomId, RoomUpdateRequest roomUpdateRequest) throws IOException {
        RoomEntity roomEntity = roomRepository.findById(UUID.fromString(roomId))
            .orElseThrow(() -> new RuntimeException("room not found"));

        mapperUtil.updateRoomFromDto(roomUpdateRequest, roomEntity);
        roomEntity = roomRepository.save(roomEntity);

        log.info("updated room {}", roomEntity);
        return roomEntity;
    }

    @Override
    public RoomEntity uploadRoomAvatar(String roomId, MultipartFile avatar) throws IOException {
        RoomEntity roomEntity = roomRepository.findById(UUID.fromString(roomId))
            .orElseThrow(() -> new RuntimeException("room not found"));

        String fileName = null;

        if(avatar != null) {
            fileName = avatar.getOriginalFilename();
        }

        if(fileName != null && !fileName.equals("")) {
            fileName = FileUtil.changeFileName(fileName, roomEntity.getId().toString());

            if(!FileUtil.isImageFile(fileName)) {
                throw new RequestException("Avatar file is not an image", HttpStatus.BAD_REQUEST);
            }

            String path = externalResources.substring("file:".length()) + "/chats-resources/avatar/";
            Files.createDirectories(Paths.get(path));
            avatar.transferTo(new File(path + fileName));
        }

        roomEntity.setAvatar(fileName != null && fileName.equals("") ? null : fileName);
        roomEntity = roomRepository.save(roomEntity);

        return roomEntity;
    }
}
