package m4;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

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

        // 定义交换机
        c.exchangeDeclare("direct_logs", BuiltinExchangeType.DIRECT);

        // 发送消息，需要携带路由键
        while (true) {
            System.out.print("输入消息： ");
            String msg = new Scanner(System.in).nextLine();
            System.out.print("输入路由键： ");
            String key = new Scanner(System.in).nextLine();
            // 第二个参数是消息上携带的路由键
            c.basicPublish("direct_logs", key, null, msg.getBytes());
        }
    }
}
