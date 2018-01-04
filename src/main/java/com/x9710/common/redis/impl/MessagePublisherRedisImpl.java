package com.x9710.common.redis.impl;

import com.x9710.common.redis.MessagePublisher;
import com.x9710.common.redis.RedisConnection;
import redis.clients.jedis.Jedis;

/**
 * 消息生产者服务 redis 实现类
 *
 * @author 杨高超
 * @since 2018-01-03
 */
public class MessagePublisherRedisImpl implements MessagePublisher {
    private RedisConnection redisConnection;

    /**
     * 可以同时向多个频道发送数据
     */
    private String[] channels;

    public void setRedisConnection(RedisConnection redisConnection) {
        this.redisConnection = redisConnection;
    }

    public void setChannels(String[] channels) {
        this.channels = channels;
    }

    public boolean sendMessage(String message) {
        Jedis jedis = null;
        try {
            if (channels != null && channels.length > 0) {
                jedis = redisConnection.getJedis();
                for (String channel : channels) {
                    jedis.publish(channel, message);
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }
}
