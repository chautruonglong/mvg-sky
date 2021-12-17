package com.mvg.sky.gateway.configuration;

import lombok.NonNull;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Order
public class HeaderFilter implements WebFilter {
    @Override
    public @NonNull Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var a = exchange.getResponse().getHeaders();
        return chain.filter(exchange);
    }
}
