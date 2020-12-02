package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHystrixService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/consumer/payment")
public class OrderHystrixController {


    @Resource
    private PaymentHystrixService paymentHystrixService;
    @GetMapping("/hystrix/ok/{id}")
    public String paymentInfoOk(@PathVariable("id") Integer id){
        return paymentHystrixService.paymentInfoOk(id);
    }

    @GetMapping("/hystrix/timeout/{id}")
    public String paymentInfoTimeOut(@PathVariable("id") Integer id){
        return paymentHystrixService.paymentInfoTimeOut(id);
    }
    @GetMapping("/hystrix/circuit/{id}")
    public String paymentInfoCircuitBreaker(@PathVariable("id") Integer id){
        return paymentHystrixService.paymentInfoCircuitBreaker(id);
    }
}
