package cn.tedu.rabbitmqspringboot.m2;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

/**
 * 合理分发
 *     1.手动ack - springboot整合后默认就是手动ack模式
 *                 消费者方法执行成后，springboot会帮助发送回执
 *     2.qos=1 - yml中配置prefetch
 *
 * 持久化
 *     1.队列持久化
 *     2.消息持久化 - 默认是持久消息
 */
@SpringBootApplication
public class Main {
    @Autowired
    private Producer p;

    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }

    @Bean
    public Queue taskQueue(){
        return new Queue("task_queue",true);
    }
    /**
     * 会被自动执行，
     *                spring扫描创建了所有对象，并完成所有注入操作后，
     *                会执行@PostConstruct方法
     *                在主线程里执行
     */
    @PostConstruct
    public void test(){
        p.send();
    }

}
