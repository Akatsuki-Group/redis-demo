package cn.tuling.redis.redisbase.basetypes;

import cn.tuling.redis.redisbase.RedisHLL;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestRedisHLL {

    @Autowired
    private RedisHLL redisHLL;

    @Test
    void testCount(){
        redisHLL.count();
    }

}
