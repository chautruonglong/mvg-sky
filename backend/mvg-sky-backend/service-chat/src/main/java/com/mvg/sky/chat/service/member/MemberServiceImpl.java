package com.mvg.sky.chat.service.member;

import com.mvg.sky.chat.dto.request.MemberAddingRequest;
import com.mvg.sky.repository.RoomAccountRepository;
import com.mvg.sky.repository.dto.query.MemberDto;
import com.mvg.sky.repository.entity.RoomAccountEntity;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final RoomAccountRepository roomAccountRepository;

    @Override
    public RoomAccountEntity addNewMember(MemberAddingRequest memberAddingRequest) {
        RoomAccountEntity roomAccountEntity = RoomAccountEntity
            .builder()
            .accountId(UUID.fromString(memberAddingRequest.getAccountId()))
            .roomId(UUID.fromString(memberAddingRequest.getRoomId()))
            .build();

        roomAccountEntity = roomAccountRepository.save(roomAccountEntity);

        log.info("add new member {}", roomAccountEntity);
        return roomAccountEntity;
    }

    @Override
    public Integer deleteMember(String roomAccountId) {
        int num = roomAccountRepository.deleteByIdAndIsDeletedFalse(UUID.fromString(roomAccountId));

        log.info("deleted member successfully, {} records changed", num);
        return num;
    }

    @Override
    public Collection<MemberDto> getAllMembersByRoomIds(List<String> roomIds, List<String> sorts, Integer offset, Integer limit) {
        Sort sort = Sort.by(Sort.Direction.ASC, sorts.toArray(String[]::new));
        Pageable pageable = PageRequest.of(offset, limit, sort);

        List<UUID> roomUuids = roomIds != null ? roomIds.stream().map(UUID::fromString).toList() : null;
        Collection<MemberDto> roomAccountEntities = roomAccountRepository.findAllMembers(roomUuids, pageable);


        log.info("find {} members", roomAccountEntities == null ? 0 : roomAccountEntities.size());
        return roomAccountEntities;
    }
}
