package com.x9710.common.redis.test;

import com.x9710.common.redis.RedisConnection;
import com.x9710.common.redis.impl.LockServiceRedisImpl;

public class RedisLockTest {

    public static void main(String[] args) {
        for (int i = 0; i < 9; i++) {
            new Thread(new Runnable() {
                public void run() {
                    RedisConnection redisConnection = RedisConnectionUtil.create();
                    LockServiceRedisImpl lockServiceRedis = new LockServiceRedisImpl();
                    lockServiceRedis.setRedisConnection(redisConnection);
                    lockServiceRedis.setDbIndex(15);
                    lockServiceRedis.setLockExpirseTime(20);
                    String key = "20171228";
                    String value = lockServiceRedis.lock(key);
                    try {
                        if (value != null) {
                            System.out.println(Thread.currentThread().getName() + " lock key = " + key + " success! ");
                            Thread.sleep(2 * 1000);
                        } else {
                            System.out.println(Thread.currentThread().getName() + " lock key = " + key + " failure! ");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (value == null) {
                            value = "";
                        }
                        System.out.println(Thread.currentThread().getName() + " unlock key = " + key + " " + lockServiceRedis.unLock(key, value));

                    }
                }
            }).start();
        }
    }
}
