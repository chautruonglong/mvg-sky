package com.mvg.sky.chat.service.member;

import com.mvg.sky.chat.dto.request.MemberAddingRequest;
import com.mvg.sky.repository.dto.query.MemberDto;
import com.mvg.sky.repository.entity.RoomAccountEntity;
import java.util.Collection;
import java.util.List;

public interface MemberService {
    RoomAccountEntity addNewMember(MemberAddingRequest memberAddingRequest);

    Integer deleteMember(String roomAccountId);

    Collection<MemberDto> getAllMembersByRoomIds(List<String> roomIds,
                                                 List<String> sorts,
                                                 Integer offset,
                                                 Integer limit);
}
