package com.x9710.common.redis.test;

import com.x9710.common.redis.RedisConnection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * redis 连接测试类
 *
 * @author 杨高超
 * @since 2017-12-09
 */
public class RedisConnectionTest {
    private RedisConnection redisConnection;

    @Before
    public void before() {
        redisConnection = RedisConnectionUtil.create();
    }

    @Test
    public void testPutGet() {
        Jedis jedis = redisConnection.getJedis();
        try {
            jedis.select(1);
            jedis.set("name", "grace");
            Assert.assertTrue("grace".equals(jedis.get("name")));
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


}
