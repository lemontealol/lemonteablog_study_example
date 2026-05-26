package com.lemontea.rocketmqpractice;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class RocketMQProducerService {

    private final RocketMQTemplate rocketMQTemplate;

    public RocketMQProducerService(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    /**
     * 同步发送消息（推荐常用）
     * @param topic Topic 名称，格式：topicName:tagName
     * @param payload 消息体
     * @param keys 业务唯一键（用于查询、幂等、事务回查）
     */
    public SendResult syncSend(String topic, String payload, String keys) {
        Message<String> message = MessageBuilder
                .withPayload(payload)
                .setHeader(RocketMQHeaders.KEYS, keys)
                .build();
        return rocketMQTemplate.syncSend(topic, message);
    }

    /**
     * 异步发送消息
     */
    public void asyncSend(String topic, String payload, String keys, SendCallback callback) {
        Message<String> message = MessageBuilder
                .withPayload(payload)
                .setHeader(RocketMQHeaders.KEYS, keys)
                .build();
        rocketMQTemplate.asyncSend(topic, message, callback);
    }

    /**
     * 单向发送（不关心结果，如日志）
     */
    public void sendOneWay(String topic, String payload) {
        rocketMQTemplate.sendOneWay(topic, MessageBuilder.withPayload(payload).build());
    }

    /**
     * 发送延时消息（RocketMQ 延时等级）
     * 延时等级：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     */
    public void sendDelay(String topic, String payload, int delayLevel) {
        rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(payload).build(), 3000, delayLevel);
    }
}