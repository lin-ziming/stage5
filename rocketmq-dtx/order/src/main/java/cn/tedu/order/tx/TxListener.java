package cn.tedu.order.tx;

import cn.tedu.order.entity.Order;
import cn.tedu.order.mapper.TxMapper;
import cn.tedu.order.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@RocketMQTransactionListener
@Slf4j
public class TxListener implements RocketMQLocalTransactionListener {
    @Autowired
    private TxMapper txMapper;
    @Autowired
    private TxOrderService txOrderService;

    /**
     * 执行本地事务
     * 保存事务状态
     * @param message
     * @param o
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(
            Message message, Object o) {
        // message - json{userId, money, xid}
        // o - Order实例

        RocketMQLocalTransactionState state;
        int status;
        try {
            Order order = (Order) o;
            txOrderService.doCreate(order);
            state = RocketMQLocalTransactionState.COMMIT;
            status = 0;
        } catch (Exception e){
            state = RocketMQLocalTransactionState.ROLLBACK;
            status = 1;
        }
        // 从message获取xid
        String json = new String((byte[]) message.getPayload());
        String xid = JsonUtil.getString(json, "xid");
        // 保存状态
        TxInfo txInfo = new TxInfo(xid,System.currentTimeMillis(),status);
        txMapper.insert(txInfo);
        return state;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        // 从message获取xid
        String json = new String((byte[]) message.getPayload());
        String xid = JsonUtil.getString(json,"xid");
        // 从数据库查询事务状态
        TxInfo txInfo = txMapper.selectById(xid);
        if (txInfo == null) {
            return RocketMQLocalTransactionState.UNKNOWN;
        }
        switch (txInfo.getStatus()){
            case 0: return RocketMQLocalTransactionState.COMMIT;
            case 1: return RocketMQLocalTransactionState.ROLLBACK;
            default: return RocketMQLocalTransactionState.UNKNOWN;
        }
    }
}
