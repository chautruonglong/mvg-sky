package com.mvg.sky.imap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ImapApplication {
    public static void main(String[] args) {
        SpringApplication.run(ImapApplication.class, args);
    }
}
