package com.example.demo;


import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.*;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

        String path = exchange.getRequest()
                              .getURI().getPath();

        if (path.startsWith("/auth/")
                || path.startsWith("/swagger-ui/")
                || path.startsWith("/v3/api-docs")) {
            return chain.filter(exchange);
        }

        String auth = exchange.getRequest()
                .getHeaders().getFirst("Authorization");

        if (auth == null || !auth.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }

        String token = auth.substring(7);

        if (!jwtUtil.validateToken(token)) {
            return unauthorized(exchange);
        }

        return chain.filter(exchange);
    }

    private Mono<Void> unauthorized(
            ServerWebExchange exchange) {

        ServerHttpResponse response =
                exchange.getResponse();

        response.setStatusCode(
                HttpStatus.UNAUTHORIZED);

        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}