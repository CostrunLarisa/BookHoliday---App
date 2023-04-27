package com.unibuc.ro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.unibuc.ro.model")
public class ProiectAWBApplication {

    public static void main(String[] args) {

        SpringApplication.run(ProiectAWBApplication.class, args);
    }

}