package com.mvg.sky.account.service.session;

import com.mvg.sky.repository.SessionRepository;
import com.mvg.sky.repository.entity.AccountEntity;
import com.mvg.sky.repository.entity.SessionEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    @NonNull
    private final SessionRepository sessionRepository;

    @Value("${com.mvg.sky.service-account.secret}")
    private String secretKey;

    @Value("${com.mvg.sky.service-account.access.expiration}")
    private Long accessTokenExpiration;

    @Value("${com.mvg.sky.service-account.refresh.expiration}")
    private Long refreshTokenExpiration;

    @Override
    public String createAccessToken(AccountEntity accountEntity) {
        String accessToken = Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .setHeaderParam("typ", "JWT")
            .setIssuer("com.mvg.sky.service-account")
            .setIssuedAt(new Date())
            .setExpiration(new Date(new Date().getTime() + accessTokenExpiration))
            .setId(accountEntity.getId().toString())
            .setSubject(accountEntity.getUsername())
            .claim("roles", accountEntity.getRoles())
            .compact();

        log.info("{} sign new accessToken: {}", accountEntity.getUsername(), accessToken);
        return accessToken;
    }

    @Override
    public String createRefreshToken(AccountEntity accountEntity) {
        String refreshToken = Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .setHeaderParam("typ", "JWT")
            .setIssuer("com.mvg.sky.service-account")
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
            .setId(accountEntity.getId().toString())
            .setSubject(accountEntity.getUsername())
            .compact();

        SessionEntity sessionEntity = SessionEntity.builder()
            .token(refreshToken)
            .accountId(accountEntity.getId())
            .build();

        sessionRepository.save(sessionEntity);

        log.info("{} sign new refreshToken: {}", accountEntity.getUsername(), refreshToken);
        return refreshToken;
    }

    @Override
    public boolean validateToken(String token) {
        Jwts.parser().setSigningKey(secretKey).parse(token);

        log.info("Token is valid");
        return true;
    }

    @Override
    public Collection<SessionEntity> getAllSessions(List<String> accountIds,
                                                    List<String> sorts,
                                                    Integer offset,
                                                    Integer limit) {
        Sort sort = Sort.by(Sort.Direction.ASC, sorts.toArray(String[]::new));
        Pageable pageable = PageRequest.of(offset, limit, sort);
        Collection<SessionEntity> sessionEntities;

        if(accountIds == null) {
            sessionEntities = sessionRepository.findAllByIsDeletedFalse(pageable);
        }
        else {
            sessionEntities = sessionRepository.findAllByAccountIdInAndIsDeletedFalse(
                accountIds.stream().map(UUID::fromString).collect(Collectors.toList()),
                pageable
            );
        }

        log.info("find {} session entities", sessionEntities == null ? 0 : sessionEntities.size());
        return sessionEntities;
    }

    @Override
    public SessionEntity getSessionById(String sessionId) {
        return sessionRepository.findByIdAndIsDeletedFalse(UUID.fromString(sessionId));
    }

    @Override
    public void deleteSessionById(String sessionId) {
        sessionRepository.deleteById(UUID.fromString(sessionId));
        log.info("delete session {} successfully", sessionId);
    }

    @Override
    public void clearSessionTable(List<String> accountIds) {
        if(accountIds == null) {
            sessionRepository.deleteAll();
        }
        else {
            sessionRepository.deleteAllByAccountIds(accountIds.stream().map(UUID::fromString).collect(Collectors.toList()));
        }
        log.info("clear records in session table successfully");
    }
}
