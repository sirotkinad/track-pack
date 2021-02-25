package com.trackpack.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.trackpack.app")
public class TrackPackApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrackPackApplication.class, args);
    }

}
