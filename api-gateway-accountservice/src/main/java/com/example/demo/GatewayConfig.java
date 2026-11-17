package com.example.demo;

import org.springframework.context.annotation.*;
import org.springframework.cloud.gateway.route.*;
import org.springframework.cloud.gateway.route.builder.*;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(
            RouteLocatorBuilder builder) {

        return builder.routes()

            .route("account-service", r -> r
                .path("/accounts/**")
                .filters(f -> f
                    .stripPrefix(1)
                    .prefixPath("/internal/accounts"))
                .uri("http://localhost:8081"))

            .build();
    }
}