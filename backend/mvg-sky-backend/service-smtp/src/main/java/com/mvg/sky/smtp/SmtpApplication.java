package com.mvg.sky.smtp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = "com.mvg.sky")
public class SmtpApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmtpApplication.class, args);
    }
}
