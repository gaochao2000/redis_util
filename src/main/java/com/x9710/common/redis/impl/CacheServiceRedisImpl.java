package com.x9710.common.redis.impl;

import com.x9710.common.redis.CacheService;
import com.x9710.common.redis.RedisConnection;
import com.x9710.common.redis.SerializeUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;

/**
 * 缓存服务 redis 实现类
 *
 * @author 杨高超
 * @since 2017-12-09
 */
public class CacheServiceRedisImpl implements CacheService {
    private static Log log = LogFactory.getLog(CacheServiceRedisImpl.class);

    private RedisConnection redisConnection;

    private Integer dbIndex;


    public void setRedisConnection(RedisConnection redisConnection) {
        this.redisConnection = redisConnection;
    }

    public void setDbIndex(Integer dbIndex) {
        this.dbIndex = dbIndex;
    }

    public void putObject(String key, Object value) {
        putObject(key, value, -1);
    }

    public void putObject(String key, Object value, int expiration) {

        Jedis jedis = null;
        try {
            jedis = redisConnection.getJedis();
            jedis.select(dbIndex);
            if (expiration > 0) {
                jedis.setex(key.getBytes(), expiration, SerializeUtil.serialize(value));
            } else {
                jedis.set(key.getBytes(), SerializeUtil.serialize(value));
            }
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }


    public Object pullObject(String key) {

        log.trace("strar find cache with " + key);
        Jedis jedis = null;
        try {
            jedis = redisConnection.getJedis();
            jedis.select(dbIndex);
            byte[] result = jedis.get(key.getBytes());
            if (result == null) {
                log.trace("can not find caceh with " + key);
                return null;
            } else {
                log.trace("find cache success with " + key);
                return SerializeUtil.unserialize(result);
            }
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    public boolean expire(String key, int expireSecond) {
        log.trace("strar set expire " + key);
        Jedis jedis = null;
        try {
            jedis = redisConnection.getJedis();
            jedis.select(dbIndex);
            return jedis.expire(key, expireSecond) == 1;
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }

    public Long ttl(String key) {
        log.trace("get set expire " + key);
        Jedis jedis = null;
        try {
            jedis = redisConnection.getJedis();
            jedis.select(dbIndex);
            return jedis.ttl(key);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return -2L;
    }

    public boolean delObject(String key) {
        log.trace("strar delete cache with " + key);
        Jedis jedis = null;
        try {
            jedis = redisConnection.getJedis();
            jedis.select(dbIndex);
            return jedis.del(key.getBytes()) > 0;
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return false;
    }

    public void clearObject() {

        Jedis jedis = null;
        try {
            jedis = redisConnection.getJedis();
            jedis.select(dbIndex);
            jedis.flushDB();
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
