package com.lemontea.rocketmqpractice;

import jakarta.annotation.PostConstruct;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 六味lemontea 2026-05-25
 * @version 1.0
 * @description
 */
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(
        topic = "order-topic",                    // 订阅的 Topic
        consumerGroup = "order-consumer-group",   // 消费者组名
        selectorExpression = "tagA",              // 消息过滤 Tag，* 表示全部
        nameServer = "127.0.0.1:9876",
        consumeMode = ConsumeMode.CONCURRENTLY,   // 消费模式：CONCURRENTLY(并发)/ORDERLY(顺序)
        messageModel = MessageModel.CLUSTERING    // 集群模式：CLUSTERING(集群)/BROADCASTING(广播)
)
public class OrderConsumer implements RocketMQListener<String> {
    @PostConstruct
    public void init() {
        System.out.println("====== OrderConsumer Bean 已初始化 ======");
    }


    @Override
    public void onMessage(String message) {
        // 1. 幂等校验（必须）
        // 2. 业务处理
        System.out.println("消费消息：" + message);
    }
}