package com.liuwenjing.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {

    @Bean
    // 负载均衡使用方式一:Eureka自带集成，使用@LoadBalanced开启负载均衡，默认使用轮询规则，也可以选择Ribbon自带的其他规则
    // 使用Ribbon自带规则时，必须使用注解@LoadBalanced
    //@LoadBalanced //负载均衡使用方式二:使用自己写的负载算法时需要去掉
    public RestTemplate getRestTemplate() {
        return  new RestTemplate();
    }
}

