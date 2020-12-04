package com.pd;

import com.pd.pojo.PdOrder;
import com.pd.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 从 rabbitmq 服务器的 orderQueue 队列，
 * 接收订单消息，调用业务代码保存订单
 *
 * 消费者代码由消息来触发执行
 */
@Component
@RabbitListener(queues = "orderQueue") //注册成为消费者等待接收消息
public class OrderConsumer {
    @Autowired
    private OrderService orderService;

    @RabbitHandler  //用来处理消息的方法
    public void receive(PdOrder pdOrder) throws Exception {
        orderService.saveOrder(pdOrder);
        System.out.println("----------订单已存储-------------");
    }
}
