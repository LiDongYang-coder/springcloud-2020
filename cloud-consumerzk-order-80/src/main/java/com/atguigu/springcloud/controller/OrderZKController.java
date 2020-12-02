package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/consumer")
@Slf4j
public class OrderZKController {
    @Autowired
    private RestTemplate restTemplate;

    //public static String PAYMENT_URL = "http://localhost:8001";
    public static String PAYMENT_URL = "http://cloud-provider-payment";

    @GetMapping(value = "/payment/zk")
    public String paymentInfo()
    {
        String result = restTemplate.getForObject(PAYMENT_URL+"/payment/zk",String.class);
        return result;
    }

}
