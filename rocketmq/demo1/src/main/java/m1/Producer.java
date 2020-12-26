package m1;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class Producer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        // 创建生产者
        DefaultMQProducer p = new DefaultMQProducer("producerGroup1");
        // 指定注册中心
        p.setNamesrvAddr("192.168.64.141:9876");
        // 启动生产者
        p.start();

        // 向消息服务发送消息
         /*
        Topic 相当于是一级分类，
        Tag 相当于是二级分类
         */
        Message msg = new Message("Topic1","TagA","Hello world!".getBytes());
        SendResult r = p.send(msg);
        System.out.println(r);

        p.shutdown();

    }
}
