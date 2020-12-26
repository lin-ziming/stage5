package cn.tedu.order.tx;

import cn.tedu.order.entity.Order;
import cn.tedu.order.feign.EasyIdGeneratorClient;
import cn.tedu.order.mapper.OrderMapper;
import cn.tedu.order.service.OrderService;
import cn.tedu.order.util.JsonUtil;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TxOrderService implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private EasyIdGeneratorClient easyIdClient;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 在这个方法中，不直接创建订单，而是发送事务消息，
     * 发送事务消息会触发监听器，执行本地事务
     * @param order
     */
    @Override
    public void create(Order order) {
        String xid = UUID.randomUUID().toString().replace("-","");

        // 创建 TxAccountMessage
        TxAccountMessage txMsg = new TxAccountMessage(order.getUserId(),order.getMoney(),xid);

        // 转成json
        String json = JsonUtil.to(txMsg);

        // 封装成 spring 通用message 对象
        Message<String> message = MessageBuilder.withPayload(json).build();

        //rocketMQTemplate.sendMessageInTransaction("order-topic",消息对象,被传递到监听器处理的对象);
        rocketMQTemplate.sendMessageInTransaction("order-topic",message,order);
    }

    /**
     * 执行保存订单业务
     * @param order
     */
    public void doCreate(Order order){
        // 调用easy-id发号器，获得订单id
        Long orderId = easyIdClient.nextId("order_business");
        order.setId(orderId);

        orderMapper.create(order);
    }
}
