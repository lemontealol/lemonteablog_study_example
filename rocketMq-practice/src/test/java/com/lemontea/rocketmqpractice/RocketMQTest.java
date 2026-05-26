package com.lemontea.rocketmqpractice;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RocketMQTest {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private RocketMQProducerService producer;

    @Autowired
    private OrderTransactionProducer transactionProducer;

    // ========== 普通同步发送 ==========
    @Test
    public void testSyncSend() {
        SendResult result = producer.syncSend("order-topic:tagA", "订单创建成功: 12345", "12345");
        System.out.println("同步发送结果：" + result.getSendStatus());
        System.out.println("消息ID：" + result.getMsgId());
    }

    // ========== 异步发送 ==========
    @Test
    public void testAsyncSend() throws InterruptedException {
        producer.asyncSend("order-topic:tagA", "异步消息内容", "key123", new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("异步发送成功，消息ID：" + sendResult.getMsgId());
            }
            @Override
            public void onException(Throwable e) {
                System.err.println("异步发送失败：" + e.getMessage());
            }
        });
        Thread.sleep(3000); // 等待回调执行
    }

    // ========== 单向发送 ==========
    @Test
    public void testOneWay() {
        producer.sendOneWay("order-topic:tagA", "单向消息，不关心结果");
        System.out.println("单向消息已发送");
    }

    // ========== 延迟消息 ==========
    @Test
    public void testDelay() {
        // 延迟等级 3 = 10秒后投递
        producer.sendDelay("order-topic:tagA", "10秒后我会被看到", 3);
        System.out.println("延迟消息已发送，将在10秒后投递");
    }

    // ========== 事务消息 ==========
    @Test
    public void testTransaction() {
        transactionProducer.createOrderWithTransaction("TX001", "事务订单数据");
        System.out.println("事务消息已发送（半消息），等待本地事务执行结果");
    }

    // ========== 直接使用 RocketMQTemplate 发送 ==========
    @Test
    public void testDirectSend() {
        SendResult result = rocketMQTemplate.syncSend("order-topic:tagA", "直接发送的消息");
        System.out.println("直接发送结果：" + result.getSendStatus());
    }
}