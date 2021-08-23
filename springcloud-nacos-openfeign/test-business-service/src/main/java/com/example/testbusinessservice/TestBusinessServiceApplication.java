package com.example.testbusinessservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.testcommonservice.feign")
public class TestBusinessServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestBusinessServiceApplication.class, args);
    }

}
