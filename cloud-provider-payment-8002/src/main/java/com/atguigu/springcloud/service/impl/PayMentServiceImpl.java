package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.dao.PayMentDao;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PayMentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayMentServiceImpl  implements PayMentService {
    @Autowired
    private PayMentDao payMentDao;
    @Override
    public int create(Payment payment) {
        return payMentDao.create(payment);
    }

    @Override
    public Payment getPayMentById(Long id) {
        return payMentDao.getPayMentById(id);
    }
}
