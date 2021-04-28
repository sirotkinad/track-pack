package com.trackpack.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.trackpack.app")
@EnableScheduling
public class TrackPackApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrackPackApplication.class, args);
    }

}
