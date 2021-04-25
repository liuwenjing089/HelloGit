package com.liuwenjing.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ConfigClientMain3366 {
    /**
        SpringCloud Bus 动态刷新全局广播：
            curl -X POST "http://localhost:3344/actuator/bus-refresh"
        SpringCloud Bus  动态刷新定点通知
            curl -X POST "http://localhost:3344/actuator/bus-refresh/{config-client:3355,config-client:3366}"
     */
    public static void main(String[] args) {
        SpringApplication.run(ConfigClientMain3366.class, args);
    }
}
