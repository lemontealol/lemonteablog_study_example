package com.lemontea.rocketmqpractice;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * @author 六味lemontea 2026-05-25
 * @version 1.0
 * @description
 */
@Service
public class OrderTransactionProducer {

    private final RocketMQTemplate rocketMQTemplate;

    public OrderTransactionProducer(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    public void createOrderWithTransaction(String orderId, String orderData) {
        Message<String> message = MessageBuilder
                .withPayload(orderData)
                .setHeader(RocketMQHeaders.KEYS, orderId)
                .build();

        // 发送事务消息（半消息）
        rocketMQTemplate.sendMessageInTransaction("order-tx-topic", message, orderId);
    }
}