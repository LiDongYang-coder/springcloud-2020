package com.atguigu.springcloud.controller;


import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PayMentService;
import com.atguigu.springcloud.vo.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PayMentService payMentService;
    @Value("${server.port}")
    private String serverPort;

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
            return new CommonResult(444,"数据查询失败,id为" +id,null);
        }
    }

    @GetMapping(value = "/lb")
    public String getPaymentIb(){
        return serverPort;
    }
}
