package com.x9710.common.redis.impl;

import com.x9710.common.redis.MessageConsumer;
import com.x9710.common.redis.RedisConnection;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * 消息消费者服务 redis 实现类
 *
 * @author 杨高超
 * @since 2018-01-03
 */
public abstract class MessageConsumerRedisImpl implements MessageConsumer {

    /**
     *
     * @param redisConnection redis 连接类
     * @param channels 订阅的频道列表
     */
    public MessageConsumerRedisImpl(RedisConnection redisConnection, String[] channels) {
        Jedis jedis = null;
        try {
            if (channels != null && channels.length > 0) {
                jedis = redisConnection.getJedis();
                jedis.subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        System.out.println("receive " + message + " from " + channel);
                        handleMessage(message);
                    }
                }, channels);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

}
