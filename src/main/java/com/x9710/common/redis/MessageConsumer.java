package com.x9710.common.redis;

/**
 * 消息消费者服务接口
 *
 * @author 杨高超
 * @since 2018-01-03
 */
public interface MessageConsumer {
    void handleMessage(String message);
}
