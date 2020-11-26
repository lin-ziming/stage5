package m1;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Haitao
 * @date 2020/11/26 16:52
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 连接
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.64.140");
        f.setUsername("admin");
        f.setPassword("admin");
        Channel c = f.newConnection().createChannel();

        // 创建helloworld队列（队列如果已经存在，则不会重复创建）
        c.queueDeclare("helloworld",false,false,false,null);

        DeliverCallback callback = new DeliverCallback() {
            @Override
            public void handle(String s, Delivery delivery) throws IOException {
                byte[] a = delivery.getBody();
                String msg = new String(a,"UTF-8");
                System.out.println("收到"+msg);
            }
        };

        CancelCallback cancel = new CancelCallback() {
            @Override
            public void handle(String s) throws IOException {

            }
        };

        //消费数据，等待从队列接收数据
        c.basicConsume("helloworld",true,callback,cancel);
    }
}
