package com.x9710.common.redis;

/**
 * 分布式锁接口
 *
 * @author 杨高超
 * @since 2017-12-14
 */
public interface LockService {
    /**
     * 通过一个key 立刻获取锁
     *
     * @param key 获取锁的 key
     * @return 如果获取成功返回锁定值，否则返回 null
     */
    String lock(String key);

    /**
     * 通过一个key 再超时时间内获取锁，如果不能马上获取锁，则等待获取，直到时间超时
     *
     * @param key 获取锁的 key
     * @return 如果获取成功返回锁定值，否则返回 null
     */
    String tryLock(String key);

    /**
     * 解锁一个分布式锁
     *
     * @param key   分布式锁 key
     * @param value 获取分布式式锁的时候获取到的值，只有用获取锁时，获取到的锁定值才能解锁。
     * @return 是否解锁成功
     */
    boolean unLock(String key, String value);
}
