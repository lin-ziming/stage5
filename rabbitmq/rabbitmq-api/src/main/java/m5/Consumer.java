package m5;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args)  throws IOException, TimeoutException {
        // 连接
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.64.140"); // wht6.cn
        // f.setPort(5672); //默认端口可以省略
        f.setUsername("admin");
        f.setPassword("admin");
        Channel c = f.newConnection().createChannel();

        // 1.定义随机队列  2.定义交换机  3.绑定，指定绑定的关键词

        // 有 rabbitmq 服务器自动命名, 默认参数：false,true,true(非持久,独占,自动删除)
        String queue = c.queueDeclare().getQueue();
        c.exchangeDeclare("topic_logs", BuiltinExchangeType.TOPIC);
        System.out.println("输入绑定键，用空格隔开：");
        String s = new Scanner(System.in).nextLine();
        String[] a = s.split("\\s+"); // ["aa",  "bb",  "cc"]
        for (String key:a) {
            c.queueBind(queue, "topic_logs", key);
        }

        // 正常的从队列消费数据
        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                // 取出消息数据，和消息上携带的路由键
                String msg = new String(message.getBody());
                String key = message.getEnvelope().getRoutingKey();
                System.out.println("收到： "+key+" - "+msg);
            }
        };
        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {
            }
        };
        c.basicConsume(queue, true, deliverCallback, cancelCallback);
    }
}
