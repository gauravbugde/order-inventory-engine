package com.enterprise.orderengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class OrderInventoryEngineApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderInventoryEngineApplication.class, args);
    }
}
