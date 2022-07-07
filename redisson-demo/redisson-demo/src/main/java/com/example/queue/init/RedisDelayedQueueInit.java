package com.example.queue.init;

import cn.hutool.core.net.NetUtil;
import com.example.queue.listener.RedisDelayedQueueListener;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 初始化队列监听
 *
 * @author yuancetian
 */
@SuppressWarnings("rawtypes")
@Component
public class RedisDelayedQueueInit implements ApplicationContextAware {

    private final Logger logger = LoggerFactory.getLogger(RedisDelayedQueueInit.class);
    @Autowired
    RedissonClient redissonClient;

    /**
     * 获取应用上下文并获取相应的接口实现类
     *
     * @param applicationContext 应用上下文
     * @throws BeansException 异常
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, RedisDelayedQueueListener> map = applicationContext.getBeansOfType(RedisDelayedQueueListener.class);
        for (Map.Entry<String, RedisDelayedQueueListener> taskEventListenerEntry : map.entrySet()) {
            String listenerName = taskEventListenerEntry.getValue().getClass().getName();
            startThread(listenerName, taskEventListenerEntry.getValue());
        }
    }

    /**
     * 启动线程获取队列*
     *
     * @param queueName                 queueName
     * @param redisDelayedQueueListener 任务回调监听
     * @param <T>                       泛型
     */
    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    private <T> void startThread(String queueName, RedisDelayedQueueListener redisDelayedQueueListener) {
        RBlockingQueue<T> blockingFairQueue = redissonClient.getBlockingQueue(queueName);
        //服务重启后，无offer，take不到信息。
        redissonClient.getDelayedQueue(blockingFairQueue);
        //由于此线程需要常驻，可以新建线程，不用交给线程池管理
        Thread thread = new Thread(() -> {
            logger.info("启动监听队列线程" + queueName);
            while (true) {
                try {
                    T t = blockingFairQueue.take();
                    logger.info("当前机器ip:{} 监听队列线程，监听名称：{},内容:{}", NetUtil.getLocalhostStr(), queueName, t);
                    redisDelayedQueueListener.invoke(t);
                } catch (Exception e) {
                    logger.info("监听队列线程错误", e);
                }
            }
        });
        thread.setName(queueName);
        thread.start();
    }

}