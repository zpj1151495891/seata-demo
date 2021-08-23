package com.example.teststorageservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.testcommonservice.feign")
@MapperScan(basePackages = "com.example.**.mapper")
public class TestStorageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestStorageServiceApplication.class, args);
    }

}
