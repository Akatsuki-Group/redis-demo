package com.example.queue;

import com.example.queue.listener.RedisDelayedQueueListener;
import com.example.queue.model.Notify;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
 
/**
 * 增加延迟信息
 *
 * @author yuancetian
 */
@SuppressWarnings("rawtypes")
@Component
public class RedisDelayedQueue {
 
    private final Logger logger = LoggerFactory.getLogger(RedisDelayedQueue.class);
 
    @Autowired
    RedissonClient redissonClient;
 
    /**
     * 添加队列
     *
     * @param t        DTO传输类
     * @param delay    时间数量
     * @param timeUnit 时间单位
     * @param <T>      泛型
     */
    public  <T> void addQueue(Notify<T> t, long delay, TimeUnit timeUnit, String queueName) {
        logger.info("添加延迟队列,监听名称:{},时间:{},时间单位:{},内容:{}" , queueName, delay, timeUnit,t);
        RBlockingQueue<Notify<T>> blockingFairQueue = redissonClient.getBlockingQueue(queueName);
        RDelayedQueue<Notify<T>> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);
        delayedQueue.offer(t, delay, timeUnit);
    }

    /**
     * 添加队列-毫秒
     *
     * @param t     DTO传输类
     * @param delay 时间数量
     * @param <T>   泛型
     */
    public <T> void addQueueMilliSeconds(Notify<T> t, long delay, Class<? extends RedisDelayedQueueListener> clazz) {
        addQueue(t, delay, TimeUnit.MILLISECONDS, clazz.getName());
    }


    /**
     * 添加队列-秒
     *
     * @param t     DTO传输类
     * @param delay 时间数量
     * @param <T>   泛型
     */
    public <T> void addQueueSeconds(Notify<T> t, long delay, Class<? extends RedisDelayedQueueListener> clazz) {
        addQueue(t, delay, TimeUnit.SECONDS, clazz.getName());
    }
 
    /**
     * 添加队列-分
     *
     * @param t     DTO传输类
     * @param delay 时间数量
     * @param <T>   泛型
     */
    public <T> void addQueueMinutes(Notify<T> t, long delay, Class<? extends RedisDelayedQueueListener> clazz) {
        addQueue(t, delay, TimeUnit.MINUTES, clazz.getName());
    }
 
    /**
     * 添加队列-时
     *
     * @param t     DTO传输类
     * @param delay 时间数量
     * @param <T>   泛型
     */
    public <T> void addQueueHours(Notify<T> t, long delay, Class<? extends RedisDelayedQueueListener> clazz) {
        addQueue(t, delay, TimeUnit.HOURS, clazz.getName());
    }
    /**
     * 添加队列-天
     *
     * @param t     DTO传输类
     * @param delay 时间数量
     * @param <T>   泛型
     */
    public <T> void addQueueDays(Notify<T> t, long delay, Class<? extends RedisDelayedQueueListener> clazz) {
        addQueue(t, delay, TimeUnit.DAYS, clazz.getName());
    }
}