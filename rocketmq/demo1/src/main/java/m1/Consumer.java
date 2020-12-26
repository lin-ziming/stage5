package m1;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class Consumer {
    public static void main(String[] args) throws MQClientException {
        // 创建消费者
        /*
        Push - 服务器主动向消费者推送
        Pull - 消费者主动向服务器请求消息
         */
        DefaultMQPushConsumer c = new DefaultMQPushConsumer("consumerGroup1");
        // 指定注册中心
        c.setNamesrvAddr("192.168.64.141:9876");
        // 从指定的Topic订阅消息
        c.subscribe("Topic1","TagA || TagB || TagC");
        // 注册消息监听器
        c.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt ext : list) {
                    String msg = new String(ext.getBody());
                    System.out.println("收到："+msg);
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//                如果处理失败则返回以下的状态：稍后重新消费，即重试
//                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });
        // 启动消费者
        c.start();
        System.out.println("消费者开始消费数据");
    }
}
