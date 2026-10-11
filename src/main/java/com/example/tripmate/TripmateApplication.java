package com.example.tripmate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TripmateApplication {

    public static void main(String[] args) {
        SpringApplication.run(TripmateApplication.class, args);
        System.out.println("=================================================");
        System.out.println("🚀 Trip Mate Backend Server is RUNNING on Port 8081");
        System.out.println("🌐 API Base URL: http://localhost:8081/api");
        System.out.println("=================================================");
    }
}
