package com.shtrade.userservice;

import com.shtrade.userservice.conf.Constant;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//@EnableEurekaClient
@SpringBootApplication
@MapperScan("com.fantj.sbmybatis.mapper")
@EnableConfigurationProperties(Constant.class)
public class UserserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserserviceApplication.class, args);
    }

}
