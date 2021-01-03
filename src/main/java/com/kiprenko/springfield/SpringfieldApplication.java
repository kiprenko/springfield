package com.kiprenko.springfield;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// Temporary disabling the Spring Security
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SpringfieldApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringfieldApplication.class, args);
    }
}
