package com.lemontea.rocketmqpractice;

import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    private final RocketMQProducerService producer;

    public DemoController(RocketMQProducerService producer) {
        this.producer = producer;
    }

    @PostMapping("/send")
    public String send(@RequestParam String orderId) {
        SendResult result = producer.syncSend("order-topic:tagA",
                "订单创建成功: " + orderId, orderId);
        return result.getSendStatus().toString();
    }
}