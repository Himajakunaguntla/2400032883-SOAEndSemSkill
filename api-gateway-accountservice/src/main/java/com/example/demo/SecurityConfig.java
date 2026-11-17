package com.example.demo;

import org.springframework.context.annotation.*;
import org.springframework.security.config.web.server.*;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(
            ServerHttpSecurity http) {

        return http
            .csrf(csrf -> csrf.disable())
            .authorizeExchange(exchange -> exchange
                .anyExchange().permitAll())
            .build();
    }
}