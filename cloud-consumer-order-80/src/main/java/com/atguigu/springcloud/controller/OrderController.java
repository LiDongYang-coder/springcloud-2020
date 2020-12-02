package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalanced;
import com.atguigu.springcloud.vo.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/consumer")
public class OrderController {
    @Autowired
    private RestTemplate restTemplate;

    //@Resource
    //private LoadBalanced loadBalanced;
    @Resource
    private DiscoveryClient discoveryClient;
    //public static String PAYMENT_URL = "http://localhost:8001";
    public static String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
    @RequestMapping("/payment/create/{serial}")
    public CommonResult<Payment> create(@PathVariable("serial") String serial){
        Payment payment = new Payment();
        payment.setSerial(serial);
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment, CommonResult.class);
    }

    @RequestMapping("/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }
    @RequestMapping("/payment/getForEntity/{id}")
    public CommonResult<Payment> getForEntity(@PathVariable("id") Long id){
        //return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }
        return new CommonResult(444,"操作错误");
    }

    @RequestMapping("/payment/postForEntity/{serial}")
    public CommonResult<Payment> postForEntity(@PathVariable("serial") String serial){
        //return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
        Payment payment = new Payment();
        payment.setSerial(serial);

        ResponseEntity<CommonResult> entity = restTemplate.postForEntity(PAYMENT_URL+"/payment/create",payment, CommonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }
        return new CommonResult(444,"操作错误");
    }

   /* @RequestMapping("/payment/lb")
    public String getPaymentLb() {
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances != null && instances.size()<=0){
            return null;
        }
        ServiceInstance instance = loadBalanced.instance(instances);
        URI uri = instance.getUri();
        return restTemplate.getForObject(uri + "/payment/lb", String.class);
    }*/


}
