package com.mvg.sky.account.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvg.sky.account.dto.jwt.JwtAccessTokenBody;
import com.mvg.sky.common.enumeration.RoleEnumeration;
import com.mvg.sky.common.exception.RequestException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class AuthorizationFilter extends OncePerRequestFilter {
    @Value("${com.mvg.sky.service-account.secret}")
    private String secretKey;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String accessToken = authorizationHeader.substring("Bearer ".length());

                ObjectMapper objectMapper = new ObjectMapper();
                Jws<Claims> jwtRefreshToken = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken);
                JwtAccessTokenBody jwtAccessTokenBody = objectMapper.convertValue(jwtRefreshToken.getBody(), JwtAccessTokenBody.class);
                RoleEnumeration[] roles = jwtAccessTokenBody.getRoles();

                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                Arrays.stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role.name())));

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    jwtAccessTokenBody.getUsername(),
                    null,
                    authorities
                );
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                filterChain.doFilter(request, response);
            }
            catch(Exception exception) {
                throw new RequestException(exception.getMessage(), HttpStatus.UNAUTHORIZED);
            }
        }
        else {
            filterChain.doFilter(request, response);
        }
    }
}
