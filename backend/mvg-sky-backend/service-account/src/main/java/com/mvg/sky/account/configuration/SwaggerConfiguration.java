package com.mvg.sky.account.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    servers = {
        @Server(url = "http://api.mvg-sky.com/api"), // unsecure gateway domain
        @Server(url = "https://api.mvg-sky.com/api"), // secure gateway domain
        @Server(url = "http://localhost:8080/api"), // unsecure gateway localhost
        @Server(url = "http://localhost:8002/api") // unsecure directly service-account localhost
    },
    info = @Info(title = "Account Service", version = "v1.0", description = "Authentication & Authorization - Local Port: 8002")
)
@SecurityScheme(name = "auth", type = SecuritySchemeType.HTTP)
public class SwaggerConfiguration {}
