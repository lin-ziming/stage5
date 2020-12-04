package cn.tedu.rabbitmqspringboot.m3;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    //1.创建随机队列  2.指定交换机logs  3.绑定
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue, //队列，随机命名，非持久，独占，自动删除
            exchange = @Exchange(name = "logs",declare = "false") //交换机
    ))
    public void receive1(String msg){
        System.out.println("消费者1收到："+msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue, //队列，随机命名，非持久，独占，自动删除
            exchange = @Exchange(name = "logs",declare = "false") //交换机
    ))
    @RabbitListener(queues = "task_queue")
    public void receive2(String msg){
        System.out.println("消费者2收到："+msg);
    }
}
