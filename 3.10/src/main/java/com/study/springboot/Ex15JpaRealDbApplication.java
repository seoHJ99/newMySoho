package com.study.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class Ex15JpaRealDbApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Ex15JpaRealDbApplication.class);
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);

    }
}