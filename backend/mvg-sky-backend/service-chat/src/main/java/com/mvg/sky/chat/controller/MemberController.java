package com.mvg.sky.chat.controller;

import com.mvg.sky.chat.dto.request.MemberAddingRequest;
import com.mvg.sky.chat.service.member.MemberService;
import com.mvg.sky.common.exception.RequestException;
import com.mvg.sky.common.response.SimpleResponseEntity;
import com.mvg.sky.repository.entity.RoomAccountEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "Member API")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<?> addMemberApi(@Valid @RequestBody MemberAddingRequest memberAddingRequest) {
        try {
            RoomAccountEntity roomAccountEntity = memberService.addNewMember(memberAddingRequest);

            return ResponseEntity.created(URI.create("/api/members/" + roomAccountEntity.getId())).body(roomAccountEntity);
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/members/{roomAccountId}")
    public ResponseEntity<?> deleteMemberApi(@PathVariable String roomAccountId) {
        try {
            int num = memberService.deleteMember(roomAccountId);

            return ResponseEntity.ok(
                SimpleResponseEntity.builder()
                    .message("Deleted member successfully")
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

    @GetMapping("/members")
    public ResponseEntity<?> getAllMembersApi(@Nullable @RequestParam("roomId") List<String> roomIds,
                                              @Nullable @RequestParam("sort") List<String> sorts,
                                              @Nullable @RequestParam("offset") Integer offset,
                                              @Nullable @RequestParam("limit") Integer limit) {
        try {
            sorts = sorts == null ? List.of("id") : sorts;
            offset = offset == null ? 0 : offset;
            limit = limit == null ? Integer.MAX_VALUE : limit;

            return ResponseEntity.ok(memberService.getAllMembersByRoomIds(roomIds, sorts, offset, limit));
        }
        catch(Exception exception) {
            log.error(exception.getMessage());
            throw new RequestException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
