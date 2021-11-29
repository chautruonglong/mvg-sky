package com.mvg.sky.smtp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class SmtpApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmtpApplication.class, args);
    }
}
