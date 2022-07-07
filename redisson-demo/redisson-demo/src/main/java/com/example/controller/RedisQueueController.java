package com.example.controller;

import com.example.queue.RedisDelayedQueue;
import com.example.queue.listener.DefaultRedisDelayedQueueListener;
import com.example.queue.model.Notify;
import com.example.queue.model.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author yuancetian
 */
@RestController
@RequestMapping("/redis-queue")
public class RedisQueueController {

    @Autowired
    private RedisDelayedQueue redisDelayedQueue;


    @RequestMapping("/add")
    public String addQueue() {
        Notify<Object> notify = new Notify<>();
        Plan plan = new Plan();
        plan.setPlanName("测试计划名称");
        plan.setPlanType("年度计划");
        plan.setPlanDesc("测试计划描述");
        notify.setObj(plan);
        notify.setDelayTime(1000);
        notify.setNextTime(new Date());
        redisDelayedQueue.addQueue(notify, 10, TimeUnit.SECONDS, DefaultRedisDelayedQueueListener.class.getName());
        return "success";
    }

}
