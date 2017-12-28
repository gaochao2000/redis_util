package com.x9710.common.redis.impl;

import com.x9710.common.redis.LockService;
import com.x9710.common.redis.RedisConnection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

/**
 * 分布式锁 redis 实现
 *
 * @author 杨高超
 * @since 2017-12-14
 */
public class LockServiceRedisImpl implements LockService {

    private static Log log = LogFactory.getLog(LockServiceRedisImpl.class);

    private static String SET_SUCCESS = "OK";

    private static String KEY_PRE = "REDIS_LOCK_";

    private DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    private RedisConnection redisConnection;

    private Integer dbIndex;

    private Integer lockExpirseTime;

    private Integer tryExpirseTime;

    public void setRedisConnection(RedisConnection redisConnection) {
        this.redisConnection = redisConnection;
    }

    public void setDbIndex(Integer dbIndex) {
        this.dbIndex = dbIndex;
    }

    public void setLockExpirseTime(Integer lockExpirseTime) {
        this.lockExpirseTime = lockExpirseTime;
    }

    public void setTryExpirseTime(Integer tryExpirseTime) {
        this.tryExpirseTime = tryExpirseTime;
    }

    public String lock(String key) {
        Jedis jedis = null;
        try {
            jedis = redisConnection.getJedis();
            jedis.select(dbIndex);
            key = KEY_PRE + key;
            String value = fetchLockValue();
            if (SET_SUCCESS.equals(jedis.set(key, value, "NX", "EX", lockExpirseTime))) {
                log.debug("Reids Lock key : " + key + ",value : " + value);
                return value;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public String tryLock(String key) {

        Jedis jedis = null;
        try {
            jedis = redisConnection.getJedis();
            jedis.select(dbIndex);
            key = KEY_PRE + key;
            String value = fetchLockValue();
            Long firstTryTime = new Date().getTime();
            do {
                if (SET_SUCCESS.equals(jedis.set(key, value, "NX", "EX", lockExpirseTime))) {
                    log.debug("Reids Lock key : " + key + ",value : " + value);
                    return value;
                }
                log.info("Redis lock failure,waiting try next");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while ((new Date().getTime() - tryExpirseTime * 1000) < firstTryTime);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public boolean unLock(String key, String value) {
        Long RELEASE_SUCCESS = 1L;
        Jedis jedis = null;
        try {
            jedis = redisConnection.getJedis();
            jedis.select(dbIndex);
            key = KEY_PRE + key;
            String command = "if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
            if (RELEASE_SUCCESS.equals(jedis.eval(command, Collections.singletonList(key), Collections.singletonList(value)))) {
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

    /**
     * 生成加锁的唯一字符串
     *
     * @return 唯一字符串
     */
    private String fetchLockValue() {
        return UUID.randomUUID().toString() + "_" + df.format(new Date());
    }
}
