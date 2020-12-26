package m2;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

public class Producer {
    static String[] msgs = {
            "15103111039,创建",
            "15103111065,创建",
            "15103111039,付款",
            "15103117235,创建",
            "15103111065,付款",
            "15103117235,付款",
            "15103111065,完成",
            "15103111039,推送",
            "15103117235,完成",
            "15103111039,完成"
    };
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        //创建生产者连接注册中心并启动
        DefaultMQProducer p = new DefaultMQProducer("producerGroup2");
        p.setNamesrvAddr("192.168.64.141:9876");
        p.start();

        //顺序发送所有消息，指定队列选择器
        for (String s : msgs) {
            String[] arr = s.split(",");
            long orderId = Long.parseLong(arr[0]);
            Message msg = new Message("Topic2", "TagA", s.getBytes());

            //第三个参数，会被传递到选择器的选择方法中
            //p.send(msg, 选择器, 选择依据);
            SendResult r =
                    p.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    Long orderId = (Long) o;
                    int index = (int) (orderId % list.size());
                    return list.get(index);
                }
            }, orderId);

            System.out.println(r);
        }

    }
}
