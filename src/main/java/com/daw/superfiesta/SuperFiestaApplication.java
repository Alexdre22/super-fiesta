package com.daw.superfiesta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SuperFiestaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuperFiestaApplication.class, args);
    }

}
