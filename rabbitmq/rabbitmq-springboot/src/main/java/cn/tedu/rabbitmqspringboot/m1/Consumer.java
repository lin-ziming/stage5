package cn.tedu.rabbitmqspringboot.m1;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    @RabbitListener(queues = "helloworld")
    public void receive(String msg){
        System.out.println("收到： "+msg);
    }
}
