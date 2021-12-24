package com.mvg.sky.account.service.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvg.sky.account.dto.jwt.JwtRefreshTokenBody;
import com.mvg.sky.repository.AccountRepository;
import com.mvg.sky.repository.SessionRepository;
import com.mvg.sky.repository.dto.query.AccountDomainDto;
import com.mvg.sky.repository.entity.AccountEntity;
import com.mvg.sky.repository.entity.DomainEntity;
import com.mvg.sky.repository.entity.SessionEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    @NonNull
    private final AccountRepository accountRepository;

    @Value("${com.mvg.sky.service-account.secret}")
    private String secretKey;

    @Value("${com.mvg.sky.service-account.access.expiration}")
    private Long accessTokenExpiration;

    @Value("${com.mvg.sky.service-account.refresh.expiration}")
    private Long refreshTokenExpiration;

    @Override
    public String createAccessToken(AccountDomainDto accountDomainDto) {
        AccountEntity accountEntity = accountDomainDto.getAccountEntity();
        DomainEntity domainEntity = accountDomainDto.getDomainEntity();

        String accessToken = Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .setHeaderParam("typ", "JWT")
            .setIssuer("com.mvg.sky.service-account")
            .setIssuedAt(new Date())
            .setExpiration(new Date(new Date().getTime() + accessTokenExpiration))
            .setId(accountEntity.getId().toString() + "@" + domainEntity.getId())
            .claim("username", accountEntity.getUsername())
            .claim("domain", domainEntity.getName())
            .claim("roles", accountEntity.getRoles())
            .compact();

        log.info("{} sign new accessToken: {}", accountEntity.getUsername(), accessToken);
        return accessToken;
    }

    @Override
    public String createRefreshToken(AccountDomainDto accountDomainDto) {
        AccountEntity accountEntity = accountDomainDto.getAccountEntity();
        DomainEntity domainEntity = accountDomainDto.getDomainEntity();

        String refreshToken = Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .setHeaderParam("typ", "JWT")
            .setIssuer("com.mvg.sky.service-account")
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
            .setId(accountEntity.getId().toString() + "@" + domainEntity.getId())
            .claim("username", accountEntity.getUsername())
            .claim("domain", domainEntity.getName())
            .compact();

        log.info("{} sign new refreshToken: {}", accountEntity.getUsername(), refreshToken);
        return refreshToken;
    }

    @Override
    public String renewAccessToken(String refreshToken) {
        SessionEntity sessionEntity = sessionRepository.findByTokenAndIsDeletedFalse(refreshToken);

        if(sessionEntity == null) {
            log.info("Refresh token is not in database");
            throw new RuntimeException("Refresh token is not in database");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Jws<Claims> jwtRefreshToken = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(refreshToken);
        JwtRefreshTokenBody jwtRefreshTokenBody = objectMapper.convertValue(jwtRefreshToken.getBody(), JwtRefreshTokenBody.class);
        AccountDomainDto accountDomainDto = accountRepository.findAccountDomainByEmail(jwtRefreshTokenBody.getUsername(), jwtRefreshTokenBody.getDomain());

        log.info("renew accessToken {}", accountDomainDto);
        return createAccessToken(accountDomainDto);
    }

    @Override
    public void validateToken(String token) {
        Jwts.parser().setSigningKey(secretKey).parse(token);

        log.info("Token is valid");
    }

    @Override
    public Collection<SessionEntity> getAllSessions(List<String> accountIds,
                                                    List<String> sorts,
                                                    Integer offset,
                                                    Integer limit) {

        Sort sort = Sort.by(Sort.Direction.ASC, sorts.toArray(String[]::new));
        Pageable pageable = PageRequest.of(offset, limit, sort);

        List<UUID> accountUuids = accountIds != null ? accountIds.stream().map(UUID::fromString).toList() : null;
        Collection<SessionEntity> sessionEntities = sessionRepository.findAllSessions(accountUuids, pageable);

        log.info("find {} session entities", sessionEntities == null ? 0 : sessionEntities.size());
        return sessionEntities;
    }

    @Override
    public SessionEntity getSessionById(String sessionId) {
        return sessionRepository.findByIdAndIsDeletedFalse(UUID.fromString(sessionId));
    }

    @Override
    public Integer deleteSessionById(String sessionId) {
        int num = sessionRepository.deleteByIdAndIsDeletedFalse(UUID.fromString(sessionId));

        log.info("delete session {} successfully, {} records updated", sessionId, num);
        return num;
    }

    @Override
    public Integer clearSessionTable(List<String> accountIds) {
        int num;
        if(accountIds == null) {
            num = sessionRepository.deleteAllAndIsDeletedFalse();
        }
        else {
            num = sessionRepository.deleteAllByAccountIdInAndIsDeletedFalse(accountIds.stream().map(UUID::fromString).collect(Collectors.toList()));
        }

        log.info("clear records in session table successfully, {} records updated", num);
        return num;
    }
}
