package com.x9710.common.redis.impl;

import com.x9710.common.redis.LBSService;
import com.x9710.common.redis.RedisConnection;
import com.x9710.common.redis.domain.GeoCoordinate;
import com.x9710.common.redis.domain.Postion;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.geo.GeoRadiusParam;

import java.util.ArrayList;
import java.util.List;

public class LBSServiceRedisImpl implements LBSService {
    private RedisConnection redisConnection;

    private Integer dbIndex;

    public void setRedisConnection(RedisConnection redisConnection) {
        this.redisConnection = redisConnection;
    }

    public void setDbIndex(Integer dbIndex) {
        this.dbIndex = dbIndex;
    }

    public boolean addPostion(Postion postion) {
        Jedis jedis = redisConnection.getJedis();
        try {
            return (1L == jedis.geoadd(postion.getType(),
                    postion.getCoordinate().getLongitude(),
                    postion.getCoordinate().getLatitude(),
                    postion.getId()));
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public List<Postion> radious(String type, GeoCoordinate center, Long distinct, Boolean asc) {
        List<Postion> postions = new ArrayList<Postion>();
        Jedis jedis = redisConnection.getJedis();
        try {
            GeoRadiusParam geoRadiusParam = GeoRadiusParam.geoRadiusParam().withCoord().withDist();
            if (asc) {
                geoRadiusParam.sortAscending();
            } else {
                geoRadiusParam.sortDescending();
            }
            List<GeoRadiusResponse> responses = jedis.georadius(type,
                    center.getLongitude(),
                    center.getLatitude(),
                    distinct.doubleValue(),
                    GeoUnit.M,
                    geoRadiusParam);
            if (responses != null) {
                for (GeoRadiusResponse response : responses) {
                    Postion postion = new Postion(response.getMemberByString(),
                            type,
                            response.getCoordinate().getLongitude(),
                            response.getCoordinate().getLatitude());
                    postion.setDistinct(response.getDistance());
                    postions.add(postion);
                }
            }
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return postions;
    }
}
