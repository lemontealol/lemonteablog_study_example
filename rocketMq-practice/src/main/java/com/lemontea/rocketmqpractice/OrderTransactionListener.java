package com.lemontea.rocketmqpractice;

/**
 * @author 六味lemontea 2026-05-25
 * @version 1.0
 * @description
 */
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;

@RocketMQTransactionListener(rocketMQTemplateBeanName = "rocketMQTemplate")
public class OrderTransactionListener implements RocketMQLocalTransactionListener {

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        // 执行本地事务（如写订单表）
        String orderId = (String) arg;
        System.out.println("执行本地事务。订单号："+orderId);
        try {
            // 1. 保存订单数据
            // 2. 如果成功 → 提交消息
            System.out.println("事务成功");
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            // 失败 → 回滚
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        // 回查本地事务状态（MQ 服务端定时调用）
        String orderId = (String) msg.getHeaders().get(RocketMQHeaders.KEYS);
        // 根据订单状态判断：已提交 → COMMIT，不存在 → ROLLBACK，不确定 → UNKNOWN
        return RocketMQLocalTransactionState.COMMIT;
    }
}