package com.zooms.dean.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class DeanAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeanAuthApplication.class, args);
    }

}
