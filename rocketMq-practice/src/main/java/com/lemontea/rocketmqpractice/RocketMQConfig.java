package com.lemontea.rocketmqpractice;

/**
 * @author 六味lemontea 2026-05-26
 * @version 1.0
 * @description
 */
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class RocketMQConfig {

    @Value("${rocketmq.name-server}")
    private String nameServer;

    @Bean
    public RocketMQTemplate rocketMQTemplate() {
        RocketMQTemplate template = new RocketMQTemplate();
        DefaultMQProducer producer = new DefaultMQProducer("my-producer-group");
        producer.setNamesrvAddr(nameServer);
        template.setProducer(producer);
        return template;
    }
}