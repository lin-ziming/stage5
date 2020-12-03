package m2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 连接
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.64.140"); // wht6.cn
        // f.setPort(5672); //默认端口可以省略
        f.setUsername("admin");
        f.setPassword("admin");
        Channel c = f.newConnection().createChannel();
        // 定义队列
        // 队列如果在服务器端已经存在，属性不可变
        c.queueDeclare("task_queue", true, false, false, null);

        // 发送消息
        // 循环输入消息发送
        /*
        输入消息：ger.err.e....er........er...e........

        消费者受到消息后，遍历字符串，每个点字符，都暂停一秒，来模拟耗时消息
         */
        while (true) {
            System.out.print("输入消息：");
            String msg = new Scanner(System.in).nextLine();
            c.basicPublish("", "task_queue", MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
        }
    }
}
