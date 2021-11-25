package com.mvg.sky.account.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScans({
    @ComponentScan("com.mvg.sky.repository"),
    @ComponentScan("com.mvg.sky.common")
})
public class ComponentScanConfiguration {}
