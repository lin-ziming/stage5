package cn.tedu.rabbitmqspringboot.m4;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
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

    @Bean
    public DirectExchange logsExchange(){
        return new DirectExchange("direct_logs",false,false);
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
