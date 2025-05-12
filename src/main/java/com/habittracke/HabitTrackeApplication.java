package com.habittracke;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HabitTrackeApplication {

    public static void main(String[] args) {
        SpringApplication.run(HabitTrackeApplication.class, args);
    }

}
