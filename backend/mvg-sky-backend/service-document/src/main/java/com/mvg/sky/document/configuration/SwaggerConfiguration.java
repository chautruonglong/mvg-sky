package com.mvg.sky.document.configuration;

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
        @Server(url = "https://localhost:8080/api"),
    },
    info = @Info(title = "Document Service", version = "v1.0", description = "API Document - Local Port: 8005")
)
@SecurityScheme(name = "auth", type = SecuritySchemeType.HTTP)
public class SwaggerConfiguration {}
