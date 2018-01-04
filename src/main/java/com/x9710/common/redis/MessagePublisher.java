package com.x9710.common.redis;

/**
 * 消息生产者服务接口
 *
 * @author 杨高超
 * @since 2018-01-03
 */
public interface MessagePublisher {
    boolean sendMessage(String message);
}
