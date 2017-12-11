package com.x9710.common.redis;

/**
 * 全局唯一标识服务接口
 *
 * @author 杨高超
 * @since 2017-12-10
 */
public interface UUIDService {

    /**
     * 每天从 1 开始生成唯一标识
     *
     * @param key     要生成唯一标识的对象
     * @param length  要生成为唯一标识后缀的长度。不包括需要附加的时间前缀
     *                如果 haveDay = false 或者 length 长度小于标识后缀的长度则无效
     * @param haveDay 是否要附加日期前缀
     * @return 唯一标识
     * @throws Exception 异常
     */
    Long fetchDailyUUID(String key, Integer length, Boolean haveDay) throws Exception;

    /**
     * 全局从 1 开始生成唯一标识
     *
     * @param key     要生成唯一标识的对象
     * @param length  要生成为唯一标识后缀的长度。不包括需要附加的时间前缀
     *                如果 haveDay = false 或者 length 长度小于标识后缀的长度则无效
     * @param haveDay 是否要附加日期前缀。
     * @return 唯一标识
     * @throws Exception 异常
     */
    Long fetchUUID(String key, Integer length, Boolean haveDay) throws Exception;
}