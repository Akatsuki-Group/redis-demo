package com.example.queue.listener;

/**
 * 队列事件监听接口，需要实现这个方法
 * @author yuancetian
 */
public interface RedisDelayedQueueListener<T> {
    /**
     * 执行方法
     *
     * @param t 队列事件
     */
    void invoke(T t);
}