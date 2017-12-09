package com.x9710.common.redis;

/**
 * 缓存服务接口
 *
 * @author 杨高超
 * @since 2017-12-09
 */
public interface CacheService {

    /**
     * 将对象存放到缓存中
     *
     * @param key   存放的key
     * @param value 存放的值
     */
    void putObject(String key, Object value);

    /**
     * 将对象存放到缓存中
     *
     * @param key        存放的key
     * @param value      存放的值
     * @param expiration 过期时间，单位秒
     */
    void putObject(String key, Object value, int expiration);

    /**
     * 从缓存中获取对象
     *
     * @param key 要获取对象的key
     * @return 如果存在，返回对象，否则，返回null
     */
    Object pullObject(String key);

    /**
     * 给缓存对象设置过期秒数
     *
     * @param key          要获取对象的key
     * @param expireSecond 过期秒数
     * @return 如果存在，返回对象，否则，返回null
     */
    boolean expire(String key, int expireSecond);

    /**
     * 获取缓存对象过期秒数
     *
     * @param key 要获取对象的key
     * @return 如果对象不存在，返回-2，如果对象没有过期时间，返回-1，否则返回实际过期时间
     */
    Long ttl(String key);

    /**
     * 从缓存中删除对象
     *
     * @param key 要删除对象的key
     * @return 如果出现错误，返回 false，否则返回true
     */
    boolean delObject(String key);

    /**
     * 从缓存中清除对象
     */

    void clearObject();

}
