package com.mvg.sky.james;

import com.mvg.sky.james.operation.DomainOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class JamesApplication {
    public static void main(String[] args) {
        SpringApplication.run(JamesApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(DomainOperation domainOperation) {
        return args -> {
            log.info("{}", domainOperation.listDomains());
        };
    }
}
