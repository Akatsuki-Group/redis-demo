package cn.tuling.redis.redisbase.redismq;

import cn.tuling.redis.redismq.PSVer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestPSVer {

    @Autowired
    private PSVer psVer;

    @Test
    void testSub(){
        psVer.sub(PSVer.RS_PS_MQ_NS+"psmq", PSVer.RS_PS_MQ_NS+"psmq2");
    }

    @Test
    void testPub(){
        psVer.pub("psmq","msgtest");
        psVer.pub("psmq2","msgtest2");
    }

}
