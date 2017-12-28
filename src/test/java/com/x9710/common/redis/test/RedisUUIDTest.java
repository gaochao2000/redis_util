package com.x9710.common.redis.test;

import com.x9710.common.redis.RedisConnection;
import com.x9710.common.redis.impl.UUIDServiceRedisImpl;

import java.util.Date;

public class RedisUUIDTest {

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(new Runnable() {
                public void run() {
                    RedisConnection redisConnection = RedisConnectionUtil.create();
                    UUIDServiceRedisImpl uuidServiceRedis = new UUIDServiceRedisImpl();
                    uuidServiceRedis.setRedisConnection(redisConnection);
                    uuidServiceRedis.setDbIndex(15);
                    try {
                        for (int i = 0; i < 100; i++) {
                            System.out.println(new Date() + " get uuid = " + uuidServiceRedis.fetchUUID("MEMBER", 8, Boolean.TRUE) + " by member in " + Thread.currentThread().getName());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            new Thread(new Runnable() {
                public void run() {
                    RedisConnection redisConnection = RedisConnectionUtil.create();
                    UUIDServiceRedisImpl uuidServiceRedis = new UUIDServiceRedisImpl();
                    uuidServiceRedis.setRedisConnection(redisConnection);
                    uuidServiceRedis.setDbIndex(15);
                    try {
                        for (int i = 0; i < 100; i++) {
                            System.out.println(new Date() + " get uuid = " + uuidServiceRedis.fetchDailyUUID("ORDER", 8, Boolean.TRUE) + " by order in " + Thread.currentThread().getName());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
