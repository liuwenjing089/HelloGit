package com.liuwenjing.springcloud.controller;

import com.liuwenjing.springcloud.entities.CommonResult;
import com.liuwenjing.springcloud.entities.Payment;
import com.liuwenjing.springcloud.service.PaymentFeignServcie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderFeignController {

    @Resource
    private PaymentFeignServcie paymentFeignServcie;

    @GetMapping(value = "/consumer/payment/feign/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
            return paymentFeignServcie.getPaymentById(id);
    }

    @GetMapping(value = "/consumer/payment/feign/timeout")
    public String paymentFeignTimeOut(){
        //openFeign-ribbon 客户端一般默认等待1秒钟
        return paymentFeignServcie.paymentFeignTimeOut();
    }

}
