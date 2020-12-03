package m1;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws Exception {
        // 连接
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.64.140");   //   wht6.cn
        f.setUsername("admin");
        f.setPassword("admin");
        Channel c = f.newConnection().createChannel();

        // 创建 helloworld 队列（队列如果已经存在，不会重复创建）
        c.queueDeclare("helloworld", false, false, false, null);

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                byte[] a = message.getBody();
                String msg = new String(a);
                System.out.println("收到： "+msg);
            }
        };
        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {
            }
        };

        // 消费数据，等待从队列接收数据
        // 第二个参数true： 由服务器对消息进行自动确认，确认后会直接删除消息
        c.basicConsume("helloworld", true, deliverCallback, cancelCallback);
    }
}
