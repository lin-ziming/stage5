package cn.tedu.rabbitmqspringboot.m1;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    // 在 RabbitAutoConfiguration 自动配置类中创建的工具对象
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(){
        amqpTemplate.convertAndSend("helloworld","Hello world!");
    }
}
