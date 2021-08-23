package com.example.testaccountservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.testcommonservice.feign")
@MapperScan(basePackages = "com.example.**.mapper")
public class TestAccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestAccountServiceApplication.class, args);
    }

}
