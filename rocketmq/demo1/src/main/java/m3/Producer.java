package m3;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Producer {
    public static void main(String[] args) throws MQClientException {
        //创建事务消息生产者
        TransactionMQProducer p =
                new TransactionMQProducer("producerGroup3");
        //连接注册中心并启动
        p.setNamesrvAddr("192.168.64.141:9876");
        p.start();

        //注册事务消息监听器，监听器实现两个功能：
        //1.执行本地事务
        //2.处理事务回查
        p.setTransactionListener(new TransactionListener() {
            Map<String,LocalTransactionState> map = new ConcurrentHashMap<>();

            //执行本地事务
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                if (Math.random() < 1) { //模拟网络中断，测试回查
                    System.out.println("本地事务执行状态未知");
                    map.put(message.getTransactionId(),LocalTransactionState.UNKNOW);
                    return LocalTransactionState.UNKNOW;
                }

                System.out.println("执行本地事务");
                if (Math.random() < 0.5) { //模拟50%的概率成功
                    System.out.println("本地事务执行成功");
                    map.put(message.getTransactionId(), LocalTransactionState.COMMIT_MESSAGE);
                    return LocalTransactionState.COMMIT_MESSAGE;//提交消息
                } else {
                    System.out.println("本地事务执行失败");
                    map.put(message.getTransactionId(),LocalTransactionState.ROLLBACK_MESSAGE);
                    return LocalTransactionState.ROLLBACK_MESSAGE;//回滚消息
                }
            }

            //处理事务回查
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                System.out.println("服务器正在回查事务状态");
                return map.get(messageExt.getTransactionId());
            }
        });

        //循环发送事务消息，发送事务消息会触发监听器执行
        while (true){
            System.out.print("输入消息：");
            String s = new Scanner(System.in).nextLine();
            Message msg = new Message("Topic3", s.getBytes());
            /*
            发送事务消息时，会触发监听器执行本地事务
            第二个参数会被传递到监听器执行本地事务的方法中
            */
            //p.sendMessageInTransaction(msg,业务数据);
            System.out.println("发送事务消息");
            p.sendMessageInTransaction(msg,null);
            System.out.println("事务消息处理结束");
        }
    }
}
