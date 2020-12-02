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
public class OrderConsulController {
    @Autowired
    private RestTemplate restTemplate;

    //public static String PAYMENT_URL = "http://localhost:8001";
    public static String PAYMENT_URL = "http://consul-provider-payment";

    @GetMapping(value = "/payment/consul")
    public String paymentInfo()
    {
        String result = restTemplate.getForObject(PAYMENT_URL+"/payment/consul",String.class);
        return result;
    }

}
