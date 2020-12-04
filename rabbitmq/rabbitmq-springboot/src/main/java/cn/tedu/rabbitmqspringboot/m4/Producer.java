package cn.tedu.rabbitmqspringboot.m4;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Producer {
    // 在 RabbitAutoConfiguration 自动配置类中创建的工具对象
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(){

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();

        /**
         * @PostConstruct 方法在主线程里执行，这里的send()方法中的
         * 死循环会阻塞主线程，所以要创建另外一个线程执行死循环，
         * 是为了不影响主线程的执行
         * lambda表达式，相当于上面代码 匿名内部类的简写
         */
        new Thread(() -> {
            while (true){
                System.out.println("输入消息： ");
                String msg = new Scanner(System.in).nextLine();
                System.out.println("输入路由键： ");
                String key = new Scanner(System.in).nextLine();
                amqpTemplate.convertAndSend("direct_logs",key,msg);
            }
        }).start();

    }
}
