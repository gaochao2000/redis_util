package com.x9710.common.redis.impl;

import com.x9710.common.redis.RedisConnection;
import com.x9710.common.redis.UUIDService;
import redis.clients.jedis.Jedis;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author 杨高超
 * @since 2017-11-19
 */
public class UUIDServiceRedisImpl implements UUIDService {
    private RedisConnection redisConnection;
    private Integer dbIndex;

    private DateFormat df = new SimpleDateFormat("yyyyMMdd");

    public void setRedisConnection(RedisConnection redisConnection) {
        this.redisConnection = redisConnection;
    }

    public void setDbIndex(Integer dbIndex) {
        this.dbIndex = dbIndex;
    }

    public Long fetchDailyUUID(String key, Integer length, Boolean haveDay) {
        Jedis jedis = null;
        try {
            jedis = redisConnection.getJedis();
            jedis.select(dbIndex);
            Calendar now = new GregorianCalendar();
            String day = df.format(now.getTime());
            key = key + "_" + day;
            Long num = jedis.incr(key);
            //设置 key 过期时间
            if (num == 1) {
                jedis.expire(key, (24 - now.get(Calendar.HOUR_OF_DAY)) * 3600 + 1800);
            }
            if (haveDay) {
                return createUUID(num, day, length);
            } else {
                return num;
            }
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Long fetchUUID(String key, Integer length, Boolean haveDay) {
        Jedis jedis = null;
        try {
            jedis = redisConnection.getJedis();
            jedis.select(dbIndex);
            Calendar now = new GregorianCalendar();
            Long num = jedis.incr(key);

            if (haveDay) {
                String day = df.format(now.getTime());
                return createUUID(num, day, length);
            } else {
                return num;
            }
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    private Long createUUID(Long num, String day, Integer length) {
        String id = String.valueOf(num);
        if (id.length() < length) {
            NumberFormat nf = NumberFormat.getInstance();
            nf.setGroupingUsed(false);
            nf.setMaximumIntegerDigits(length);
            nf.setMinimumIntegerDigits(length);
            id = nf.format(num);
        }
        return Long.parseLong(day + id);
    }

}
