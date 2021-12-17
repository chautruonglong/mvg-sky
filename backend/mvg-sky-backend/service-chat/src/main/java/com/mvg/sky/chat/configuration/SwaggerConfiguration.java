package com.mvg.sky.chat.configuration;

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
        @Server(url = "http://localhost:8001/api")
    },
    info = @Info(title = "Chat Service", version = "v1.0", description = "Chat system - Local Port: 8001")
)
@SecurityScheme(name = "auth", type = SecuritySchemeType.HTTP)
public class SwaggerConfiguration {}
