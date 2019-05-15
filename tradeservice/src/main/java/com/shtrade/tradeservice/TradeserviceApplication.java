package com.shtrade.tradeservice;

import com.shtrade.tradeservice.conf.Constant;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@MapperScan("com.fantj.sbmybatis.mapper")
@EnableConfigurationProperties(Constant.class)
public class TradeserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradeserviceApplication.class, args);
    }

}
