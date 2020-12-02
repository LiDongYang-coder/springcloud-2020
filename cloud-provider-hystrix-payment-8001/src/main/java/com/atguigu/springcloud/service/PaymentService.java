package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.stereotype.Service;


@DefaultProperties(defaultFallback = "gloableFallBackHandler")
@Service
public class PaymentService {

    public String paymentInfoOK(Integer id) {
        return "线程池： " + Thread.currentThread().getName() +"\t"+ "paymentInfoOK(),id:"+ id+ "\t" +" \"O(∩_∩) 成功返回哈哈哈";
    }
   /* @HystrixCommand(fallbackMethod = "paymentInfoTimeOutHandler",
            commandProperties =@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    )*/
    @HystrixCommand
    public String paymentInfoTimeOut(Integer id){

        //System.out.println("第"+count.getAndIncrement()+"请求");
        Integer timeOutNumber = 5;
       // int age  = 10/0;
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "线程池:  "+Thread.currentThread().getName()+" id:  "+id+"\t"+"O(∩_∩)O哈哈~"+"  耗时(秒): "+timeOutNumber;
    }

    public String paymentInfoTimeOutHandler (Integer id){
        return "服务调用超时或报错,请稍后再试..........!";
    }

    public String gloableFallBackHandler(){
        return "服务调用超时或报错,请稍后再试哦扫地机公积金..........!";
    }


    @HystrixCommand(fallbackMethod = "paymentCircuitBreakerHandler",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),//失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(Integer id){

        if(id < 0) {
            throw  new RuntimeException("****id 不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t"+"调用成功，流水号: " + serialNumber;
    }

    public String paymentCircuitBreakerHandler(Integer id){
        return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " +id;
    }
}
