package com.example.modularquizappmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan(basePackages = {"com.example.modularquizappmvc","com.example.modularquizappcommon"})
@EnableJpaRepositories("com.example.modularquizappcommon")
@EntityScan("com.example.modularquizappcommon")
public class ModularQuizAppMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModularQuizAppMvcApplication.class, args);
    }


}
