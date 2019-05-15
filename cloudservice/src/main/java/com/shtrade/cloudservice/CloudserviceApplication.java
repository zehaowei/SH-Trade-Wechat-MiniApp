package com.shtrade.cloudservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CloudserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudserviceApplication.class, args);
    }

}
