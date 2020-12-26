package m2;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class Consumer {
    public static void main(String[] args) throws MQClientException {
        //创建消费者，连接注册中心
        DefaultMQPushConsumer c = new DefaultMQPushConsumer("consumerGroup2");
        c.setNamesrvAddr("192.168.64.141:9876");

        //订阅消息
        c.subscribe("Topic2","*");
        //注册顺序消息监听器
        c.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                for (MessageExt ext : list) {
                    String msg = new String(ext.getBody());
                    System.out.println("收到："+msg);
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        //启动
        c.start();
        System.out.println("消费者开始接收消息");
    }
}
