package com.liuwenjing.springcloud.service;

import com.liuwenjing.springcloud.entities.CommonResult;
import com.liuwenjing.springcloud.entities.Payment;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("CLOUD-PAYMENT-SERVICE")
public interface PaymentFeignServcie {

    /*
        使用OpenFeign
        实际访问地址为@GetMapping(value = "/consumer/payment/feign/get/{id}")，绑定的是paymentFeignServcie.getPaymentById(id)
        方法，paymentFeignServcie.getPaymentById(id)方法实际绑定的是
        服务提供方CLOUD-PAYMENT-SERVICE的@GetMapping(value = "/payment/get/{id}")方法
     */
    @GetMapping(value = "/payment/get/{id}")
    public  CommonResult getPaymentById(@PathVariable("id") Long id);

    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeOut();
}
