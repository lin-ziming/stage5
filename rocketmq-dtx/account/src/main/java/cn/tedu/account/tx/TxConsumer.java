package cn.tedu.account.tx;


import cn.tedu.account.service.AccountService;
import cn.tedu.account.util.JsonUtil;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "account-consumer-group",topic = "order-topic")
public class TxConsumer implements RocketMQListener<String> {
    @Autowired
    private AccountService accountService;

    @Override
    public void onMessage(String json) {
        // json --> TxAccountMessage
        TxAccountMessage msg = JsonUtil.from(json, TxAccountMessage.class);

        // 调用业务代码，扣减账户金额
        accountService.decrease(msg.getUserId(),msg.getMoney());
    }
}
