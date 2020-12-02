package com.atguigu.springcloud.controller;


import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PayMentService;
import com.atguigu.springcloud.vo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentController {
    @Autowired
    private PayMentService payMentService;

    @Value("${server.port}")
    private String serverPort;
    @Autowired
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/create")
    public CommonResult create(@RequestBody Payment payment){
       int num= payMentService.create(payment);
       if (num > 0 ) {
         return new CommonResult(200,"插入数据库成功");
       }else {
           return new CommonResult(444,"插入数据库失败");
       }
    }

    @GetMapping(value = "/get/{id}")
    public CommonResult getById(@PathVariable Long id){
        Payment payment = payMentService.getPayMentById(id);
        if (payment != null) {
            return new CommonResult(200,"数据查询成功,serverPort " + serverPort,payment);
        }else {
            return new CommonResult(444,"数据查询失败,id为 serverPort " +id + serverPort,null);
        }
    }

    @GetMapping(value = "/getDiscovery")
    public Object getDiscovery(){
        List<String> services = discoveryClient.getServices();
        for (String element :services) {
            log.debug("******************element*****：" + element);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance serviceInstance :instances) {
            log.debug("InstanceId : " +serviceInstance.getInstanceId() +"   Host :" +serviceInstance.getHost() +" Uri :" +serviceInstance.getUri());
        }
        return discoveryClient;
    }

    @GetMapping(value = "/lb")
    public String getPaymentIb(){
        return serverPort;
    }
    @GetMapping(value = "/feign/timeout")
    public String feignPaymentTimeout(){
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (Exception e){
            e.printStackTrace();
        }
        return serverPort;
    }
}
