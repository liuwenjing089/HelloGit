package com.liuwenjing.springcloud.controller;

import com.liuwenjing.springcloud.entities.CommonResult;
import com.liuwenjing.springcloud.entities.Payment;
import com.liuwenjing.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    //public static final String PAYMENT_URL= "http://localhost:8001";

    // 通过在eureka上注册过的微服务名称调用 spring-application-name
    public static final String PAYMENT_SRV="http://CLOUD-PAYMENT-SERVICE";
    // 两个服务间的远程调用 restTemplate
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalancer loadBalancer;

    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){

        return restTemplate.postForObject(PAYMENT_SRV+"/payment/create", payment, CommonResult.class);
    }

    // 消费端都使用GetMapping
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_SRV+ "/payment/get/"+ id, CommonResult.class);
    }

    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable("id") Long id){

        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_SRV + "/payment/get/" + id, CommonResult.class);

        if(entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        } else {
            return new CommonResult<>(444,"操作失败",null);
        }

    }

    @GetMapping(value = "/consumer/discovery")
    public Object discovery(){ 
        List<String> services = discoveryClient.getServices();

        for (String service : services) {
            log.info("*****element:" + service);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-ORDER-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId()+"\t" + instance.getHost() +
                    "\t" + instance.getPort() +"\t" + instance.getUri());
        }

        return  this.discoveryClient;

    }

    @GetMapping(value = "/consumer/payment/lb")
    public String getPaymentLB(){
        // 根据服务名获取当前服务的所有实例对象
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");

        if(instances == null || instances.size() <= 0){
            return null;
        }

        ServiceInstance instance = loadBalancer.instances(instances);
        URI uri = instance.getUri();
        System.out.println(uri);

        return  restTemplate.getForObject(uri+"/payment/lb",String.class);

    }

}
