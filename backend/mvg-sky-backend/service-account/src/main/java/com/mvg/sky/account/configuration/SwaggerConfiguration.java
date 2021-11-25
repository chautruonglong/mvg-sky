package com.mvg.sky.account.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "service-account", version = "v1.0", description = "Authentication & Authorization - Local Port: 8002"))
public class SwaggerConfiguration {}
