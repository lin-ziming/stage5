package m3;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 连接
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.64.140"); // wht6.cn
        // f.setPort(5672); //默认端口可以省略
        f.setUsername("admin");
        f.setPassword("admin");
        Channel c = f.newConnection().createChannel();

        // 1.定义随机队列  2.定义交换机  3.绑定
        String queue = UUID.randomUUID().toString();
        c.queueDeclare(queue, false, true, true, null);
        c.exchangeDeclare("logs", BuiltinExchangeType.FANOUT);
        // 对 fanout 交换机，第三个参数无效
        c.queueBind(queue, "logs", "");
        // 正常从队列接收消息
        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                String msg = new String(message.getBody());
                System.out.println("收到： "+msg);
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
