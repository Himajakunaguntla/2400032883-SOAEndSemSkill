package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI bankingAPI() {

        return new OpenAPI()
            .info(new Info()
                .title("Banking API")
                .version("1.0")
                .description("JWT Account Gateway API"))

            .schemaRequirement("bearerAuth",
                new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT"))

            .addSecurityItem(
                new SecurityRequirement()
                    .addList("bearerAuth"))

            .path("/accounts/all",
                new PathItem().get(
                    new io.swagger.v3.oas.models.Operation()
                        .summary("Get all accounts")
                        .description("Fetch all accounts from PostgreSQL")
                        .responses(new io.swagger.v3.oas.models.responses.ApiResponses()
                            .addApiResponse("200",
                                new ApiResponse().description("Accounts retrieved"))
                            .addApiResponse("401",
                                new ApiResponse().description("Invalid or missing token"))
                        )
                )
            );
    }
}
