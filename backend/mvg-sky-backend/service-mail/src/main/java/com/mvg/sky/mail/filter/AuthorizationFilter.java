package com.mvg.sky.mail.filter;

import com.mvg.sky.common.exception.RequestException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
//@Component
@AllArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    private final SessionConsumer sessionConsumer;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring("Bearer ".length());

            try {
                sessionConsumer.validateToken(accessToken);
                doFilter(request, response, filterChain);
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
