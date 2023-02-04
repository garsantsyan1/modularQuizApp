package com.example.modularquizapprest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"com.example.modularquizapprest","com.example.modularquizappcommon"})
@EnableJpaRepositories("com.example.modularquizappcommon")
@EntityScan("com.example.modularquizappcommon")
@SpringBootApplication
public class ModularQuizAppRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModularQuizAppRestApplication.class, args);
    }

}
