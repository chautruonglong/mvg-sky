package com.mvg.sky.mail.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    servers = {
        @Server(url = "http://api.mvg-sky.com/api"),
        @Server(url = "https://api.mvg-sky.com/api"),
        @Server(url = "http://localhost:8080/api"),
        @Server(url = "http://localhost:8004/api")
    },
    info = @Info(title = "Mail Service", version = "v1.0", description = "Mail System - Local Port: 8004")
)
@SecurityScheme(name = "auth", type = SecuritySchemeType.HTTP)
public class SwaggerConfiguration {}
