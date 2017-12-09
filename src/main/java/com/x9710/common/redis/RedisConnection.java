package com.x9710.common.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConnection {
    /**
     * redis 连接池配置信息
     */
    private JedisPoolConfig jedisPoolConfig;
    /**
     * redis 服务器地址
     */
    private String ip;

    /**
     * redis 服务器端口
     */
    private Integer port;

    /**
     * redis 服务器密码
     */
    private String pwd;

    /**
     * redis 服务器连接超时时间
     */
    private Integer timeOut;

    /**
     * redis 连接客户端名称
     */
    private String clientName = null;

    private JedisPool jedisPool;

    public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
        this.jedisPoolConfig = jedisPoolConfig;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    private void buildConnection() {
        if (jedisPool == null) {
            if (jedisPoolConfig == null) {
                jedisPool = new JedisPool(new JedisPoolConfig(), ip, port, timeOut, pwd, 0, clientName);
            } else {
                jedisPool = new JedisPool(jedisPoolConfig, ip, port, timeOut, pwd, 0, clientName);
            }
        }
    }

    public Jedis getJedis() {
        buildConnection();
        if (jedisPool != null) {
            return jedisPool.getResource();
        }
        return null;
    }
}
