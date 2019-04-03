package com.viaplay.viaplay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableCaching
@SpringBootApplication
public class ViaplayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ViaplayApplication.class, args);
    }

}
