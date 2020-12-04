package cn.tedu.rabbitmqspringboot.m1;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Main {
    @Autowired
    private Producer p;

    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }
    // import org.springframework.amqp.core.Queue;
    @Bean
    public Queue helloworldQueue(){
        // return new Queue("helloworld"); //true,false,false
        return new Queue("helloworld",false);
    }
    /**
     * @PostConstruct 方法会被自动执行，
     *                spring扫描创建了所有对象，并完成所有注入操作后，
     *                会执行@PostConstruct方法
     */
    @PostConstruct
    public void test(){
        p.send();
        System.out.println("消息已发送");
    }

}
