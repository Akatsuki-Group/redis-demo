package com.example.queue.listener;

import com.example.queue.model.Notify;
import com.example.queue.model.Plan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author yuancetian
 */
@Slf4j
@Component
public class DefaultRedisDelayedQueueListener implements RedisDelayedQueueListener<Notify<Plan>> {
    @Override
    public void invoke(Notify<Plan> t) {
        log.info("获取到延迟队列信息,内容:{}", t);
    }
}
