package com.eric.rosca;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.eric"})
//@EntityScan("com.chiko.persist")
public class RoscaApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RoscaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


    }
}
