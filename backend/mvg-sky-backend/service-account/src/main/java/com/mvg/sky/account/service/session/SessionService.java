package com.mvg.sky.account.service.session;

import com.mvg.sky.repository.dto.query.AccountDomainDto;
import com.mvg.sky.repository.entity.SessionEntity;
import java.util.Collection;
import java.util.List;

public interface SessionService {
    String createAccessToken(AccountDomainDto accountDomainDto);

    String createRefreshToken(AccountDomainDto accountDomainDto);

    String renewAccessToken(String refreshToken);

    void validateToken(String token);

    Collection<SessionEntity> getAllSessions(List<String> accountIds,
                                             List<String> sorts,
                                             Integer offset,
                                             Integer limit);

    SessionEntity getSessionById(String sessionId);

    Integer deleteSessionById(String sessionId);

    Integer clearSessionTable(List<String> accountIds);
}
