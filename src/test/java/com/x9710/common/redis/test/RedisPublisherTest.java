package com.x9710.common.redis.test;

import com.x9710.common.redis.RedisConnection;
import com.x9710.common.redis.impl.MessagePublisherRedisImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 消息发送服务测试类
 *
 * @author 杨高超
 * @since 2018-01-03
 */
public class RedisPublisherTest {
    private MessagePublisherRedisImpl messagePublisherRedis;

    @Before
    public void before() {
        RedisConnection redisConnection = RedisConnectionUtil.create();
        messagePublisherRedis = new MessagePublisherRedisImpl();
        messagePublisherRedis.setRedisConnection(redisConnection);
        messagePublisherRedis.setChannels(new String[]{"channel1", "channel2"});
    }

    @Test
    public void publisherTest() {
        for (int i = 0; i < 3; i++) {
            Assert.assertTrue(messagePublisherRedis.sendMessage("message" + i));
        }
    }
}
