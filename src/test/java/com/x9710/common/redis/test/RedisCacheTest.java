package com.x9710.common.redis.test;

import com.x9710.common.redis.RedisConnection;
import com.x9710.common.redis.impl.CacheServiceRedisImpl;
import com.x9710.common.redis.test.domain.Student;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 缓存服务测试类
 *
 * @author 杨高超
 * @since 2017-12-09
 */
public class RedisCacheTest {
    private CacheServiceRedisImpl cacheService;

    @Before
    public void before() {
        RedisConnection redisConnection = RedisConnectionUtil.create();
        cacheService = new CacheServiceRedisImpl();
        cacheService.setDbIndex(2);
        cacheService.setRedisConnection(redisConnection);
    }

    @Test
    public void testStringCache() {
        String key = "name";
        String value = "grace";
        cacheService.putObject(key, value);
        String cachValue = (String) cacheService.pullObject(key);
        //检查从缓存中获取的字符串是否等于原始的字符串
        Assert.assertTrue(value.equals(cachValue));
        //检查从缓存删除已有对象是否返回 true
        Assert.assertTrue(cacheService.delObject(key));
        //检查从缓存删除已有对象是否返回 false
        Assert.assertFalse(cacheService.delObject(key + "1"));
        //检查从缓存获取已删除对象是否返回 null
        Assert.assertTrue(cacheService.pullObject(key) == null);
    }


    @Test
    public void testObjectCache() {
        Student oriStudent = new Student();
        oriStudent.setId("2938470s9d8f0");
        oriStudent.setName("柳白猿");
        oriStudent.setAge(36);
        cacheService.putObject(oriStudent.getId(), oriStudent);
        Student cacheStudent = (Student) cacheService.pullObject(oriStudent.getId());
        Assert.assertTrue(oriStudent.equals(cacheStudent));
        Assert.assertTrue(cacheService.delObject(oriStudent.getId()));
        Assert.assertTrue(cacheService.pullObject(oriStudent.getId()) == null);
    }

    @Test
    public void testExpireCache() {
        String key = "name";
        String value = "grace";
        cacheService.putObject(key, value);
        cacheService.expire(key, 300);
        String cachValue = (String) cacheService.pullObject(key);
        Assert.assertTrue(value.equals(cachValue));
        Long ttl = cacheService.ttl(key);
        Assert.assertTrue(ttl > 250 && ttl <= 300);
        Assert.assertTrue(value.equals(cachValue));
        Assert.assertTrue(cacheService.delObject(key));
    }
}
