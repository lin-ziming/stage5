package m1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class Producer {
    public static void main(String[] args) throws Exception {
        // 建立连接
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.64.140");  //   wht6.cn
        f.setPort(5672);
        f.setUsername("admin");
        f.setPassword("admin");
        Connection conn = f.newConnection();
        Channel c = conn.createChannel();

        // 让服务器创建队列 helloworld（已经存在则不重复创建）
        // 队列的参数: helloworld, 是否是持久队列, 是否排他（独占）队列, 是否自动删除
        c.queueDeclare(
                "helloworld",
                false,
                false,
                false,
                null); //其他参数属性，没有给null

        // 消息发送到 helloworld 队列
        // 参数：
        //   1. 默认的交换机(讲第三个模式时再解释)
        //   3. 其他的消息属性配置,如果没有给null值
        c.basicPublish(
                "",
                "helloworld",
                null,
                "Hello world!".getBytes());

        System.out.println("消息已发送");

    }
}
