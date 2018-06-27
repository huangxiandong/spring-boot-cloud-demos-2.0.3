package com.xiyuan.springcloud;

import com.xiyuan.springcloud.feign.config.DefaultFeignClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.xiyuan")
@EnableFeignClients(defaultConfiguration = DefaultFeignClientConfig.class)
@EnableCircuitBreaker
@EnableAutoConfiguration
public class FrontServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontServiceApplication.class, args);
    }

}
