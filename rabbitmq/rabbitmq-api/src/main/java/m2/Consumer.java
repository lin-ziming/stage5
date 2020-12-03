package m2;

import com.rabbitmq.client.*;

import java.io.IOException;
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

        // 定义队列
        c.queueDeclare("task_queue", true, false, false, null);

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                // 遍历消息字符串，每个点字符都暂停一秒
                String msg = new String(message.getBody());
                System.out.println("收到： "+msg);
                for (int i = 0; i <msg.length(); i++) {
                    if ('.' == msg.charAt(i)) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                    }
                }

                //向服务器发送消息的回执
                // c.basicAck(回执(long类型的编码), 是否一次确认多条消息);
                c.basicAck(message.getEnvelope().getDeliveryTag(), false);
                System.out.println("消息处理结束");
            }
        };
        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {
            }
        };

        // 每次只接收处理1条消息，处理完成之前不接收下一条
        // 必须在手动 ACK 模式下才有效
        c.basicQos(1);

        // 消费数据
        // 第二个参数： true - 自动确认     false - 手动确认
        c.basicConsume("task_queue", false, deliverCallback, cancelCallback);
    }
}
