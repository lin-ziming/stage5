package m3;

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

        // 定义交换机，服务器如果已经存在这个交换机，不会重复创建
        //c.exchangeDeclare("logs", "fanout");
        c.exchangeDeclare("logs", BuiltinExchangeType.FANOUT);

        // 向交换机发送消息
        while (true) {
            System.out.print("输入消息：");
            String msg = new Scanner(System.in).nextLine();
            // 第一个参数： 交换机
            // 第二个参数： 选择队列，对fanout交换机无效
            c.basicPublish("logs", "", null, msg.getBytes());
        }
    }
}

