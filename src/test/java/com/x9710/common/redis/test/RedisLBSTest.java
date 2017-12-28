package com.x9710.common.redis.test;

import com.x9710.common.redis.RedisConnection;
import com.x9710.common.redis.domain.GeoCoordinate;
import com.x9710.common.redis.domain.Postion;
import com.x9710.common.redis.impl.CacheServiceRedisImpl;
import com.x9710.common.redis.impl.LBSServiceRedisImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * LBS服务测试类
 *
 * @author 杨高超
 * @since 2017-12-28
 */
public class RedisLBSTest {
    private CacheServiceRedisImpl cacheService;
    private LBSServiceRedisImpl lbsServiceRedis;
    private String type = "SHOP";
    private GeoCoordinate center;

    @Before
    public void before() {
        RedisConnection redisConnection = RedisConnectionUtil.create();
        lbsServiceRedis = new LBSServiceRedisImpl();
        lbsServiceRedis.setDbIndex(15);
        lbsServiceRedis.setRedisConnection(redisConnection);
        Postion postion = new Postion("2017122801", type, 91.118970, 29.654210);
        lbsServiceRedis.addPostion(postion);
        postion = new Postion("2017122802", type, 116.373472, 39.972528);
        lbsServiceRedis.addPostion(postion);
        postion = new Postion("2017122803", type, 116.344820, 39.948420);
        lbsServiceRedis.addPostion(postion);
        postion = new Postion("2017122804", type, 116.637920, 39.905460);
        lbsServiceRedis.addPostion(postion);
        postion = new Postion("2017122805", type, 118.514590, 37.448150);
        lbsServiceRedis.addPostion(postion);
        postion = new Postion("2017122806", type, 116.374766, 40.109508);
        lbsServiceRedis.addPostion(postion);
        center = new GeoCoordinate();
        center.setLongitude(116.373472);
        center.setLatitude(39.972528);
    }

    @Test
    public void test10KMRadious() {
        List<Postion> postions = lbsServiceRedis.radious(type, center, 1000 * 10L, true);
        Assert.assertTrue(postions.size() == 2 && exist(postions, "2017122802") && exist(postions, "2017122803"));
    }

    @Test
    public void test50KMRadious() {
        List<Postion> postions = lbsServiceRedis.radious(type, center, 1000 * 50L, true);
        Assert.assertTrue(postions.size() == 4
                && exist(postions, "2017122802")
                && exist(postions, "2017122803")
                && exist(postions, "2017122806")
                && exist(postions, "2017122804"));
    }

    private boolean exist(List<Postion> postions, String key) {
        if (postions != null) {
            for (Postion postion : postions) {
                if (postion.getId().equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Before
    public void after() {
        RedisConnection redisConnection = RedisConnectionUtil.create();
        cacheService = new CacheServiceRedisImpl();
        cacheService.setDbIndex(15);
        cacheService.setRedisConnection(redisConnection);
        cacheService.delObject(type);
    }
}
