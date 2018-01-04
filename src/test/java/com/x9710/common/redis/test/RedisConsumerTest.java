package com.x9710.common.redis.test;

import com.x9710.common.redis.RedisConnection;
import com.x9710.common.redis.impl.MessageConsumerRedisImpl;
import org.junit.Test;

/**
 * 消息消费服务测试类
 *
 * @author 杨高超
 * @since 2018-01-03
 */
public class RedisConsumerTest {

    @Test
    public void consumerTest() {
        RedisConnection redisConnection = RedisConnectionUtil.create();
        new MessageConsumerRedisImpl(redisConnection, new String[]{"channel1", "channel2"}) {
            public void handleMessage(String message) {
                System.out.println(message);
            }
        };
    }

}
