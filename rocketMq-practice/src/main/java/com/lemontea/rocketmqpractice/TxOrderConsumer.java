package com.lemontea.rocketmqpractice;

/**
 * @author 六味lemontea 2026-05-26
 * @version 1.0
 * @description
 */

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(
        topic = "order-tx-topic",
        consumerGroup = "tx-consumer-group",
        nameServer = "127.0.0.1:9876"
)
public class TxOrderConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println("事务消息消费者收到：" + message);
    }
}