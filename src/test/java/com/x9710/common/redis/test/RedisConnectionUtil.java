package com.x9710.common.redis.test;

import com.x9710.common.redis.RedisConnection;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis 连接测试辅助类
 *
 * @author 杨高超
 * @since 2017-12-09
 */
public class RedisConnectionUtil {
    public static RedisConnection create() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(50);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(1);
        RedisConnection redisConnection = new RedisConnection();
        redisConnection.setIp("10.110.2.56");
        redisConnection.setPort(52981);
        redisConnection.setPwd("hhSbcpotThgWdnxJNhrzwstSP20DvYOldkjf");
        redisConnection.setClientName(Thread.currentThread().getName());
        redisConnection.setTimeOut(600);
        redisConnection.setJedisPoolConfig(jedisPoolConfig);
        return redisConnection;
    }
}
