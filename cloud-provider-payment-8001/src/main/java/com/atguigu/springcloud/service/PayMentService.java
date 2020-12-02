package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.Payment;

public interface PayMentService {
    public int create(Payment payment);

    public Payment getPayMentById(Long id);
}
