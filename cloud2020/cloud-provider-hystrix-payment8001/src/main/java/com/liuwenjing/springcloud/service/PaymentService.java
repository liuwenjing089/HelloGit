package com.liuwenjing.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    // =======服务降级
    /*
        正常访问，一定成功的方法
     */
    public String paymentInfo_OK(Integer id){
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_OK " + id + "\t" + "O(∩_∩)O";
    }

    /**
     *
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            @HystrixProperty( name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public String paymentInfo_TimeOut(Integer id){

        //int age = 10/0;
        int consumeTime = 3;
        try{
            TimeUnit.SECONDS.sleep(consumeTime);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_TimeOut " + id + "\t" + "O(∩_∩)O" + "耗时(秒):" + "运行成功";
    }

    public String paymentInfo_TimeOutHandler(Integer id){
        //return "/(ㄒoㄒ)/~~调用支付接口超时或异常，\t" + "\t 当前线程池名称:" + Thread.currentThread().getName();
        return "线程池:" + Thread.currentThread().getName() + "   8001系统繁忙或运行出错，请稍后再试，id:" + id + "\t" + "/(ㄒoㄒ)/~~";
    }

    // ===========服务熔断
    // HystrixCommandProperties类中有所有可配置的name属性
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),              //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),    //请求数达到后才计算
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //休眠时间窗
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),  //错误率达到多少跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if(id < 0){
            throw  new RuntimeException("****id 不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();

        return  Thread.currentThread().getName() + "\t" + "调用成功，流水号：" + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能为负数,请稍后再试， o(╥﹏╥)o id: " + id;
    }
}
