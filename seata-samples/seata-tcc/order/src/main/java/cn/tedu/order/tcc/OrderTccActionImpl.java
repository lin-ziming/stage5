package cn.tedu.order.tcc;

import cn.tedu.order.entity.Order;
import cn.tedu.order.mapper.OrderMapper;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author Haitao
 * @date 2020/12/14 16:15
 */
@Component
public class OrderTccActionImpl implements OrderTccAction {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public boolean prepareCreateOrder(BusinessActionContext ctx, Long orderId, Long userId, Long productId, Integer count, BigDecimal money) {
        orderMapper.create(new Order(
                orderId,userId,productId,count,money,0));
        //保存第一阶段的成功标记
        ResultHolder.setResult(OrderTccAction.class,ctx.getXid(),"p");
        return true;
    }

    @Override
    public boolean commit(BusinessActionContext ctx) {
        //如果标记不存在，不重复地执行提交
        String p = ResultHolder.getResult(OrderTccAction.class, ctx.getXid());
        if (p==null) {
            return true;
        }
        /**
         * 上下文对象在从第一阶段向第二阶段传递时，先转成了 json 数据，
         * 然后再还原成上下文对象
         * 其中较小的整数值，会转成 Integer 类型
         */
        Long orderId =
                Long.valueOf(ctx.getActionContext("orderId").toString());

        orderMapper.updateStatus(orderId,1);

        //提交完成后，删除标记
        ResultHolder.removeResult(OrderTccAction.class,ctx.getXid());
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext ctx) {
        //如果标记不存在，不重复地执行回滚
        String p = ResultHolder.getResult(OrderTccAction.class, ctx.getXid());
        if (p==null) {
            return true;
        }
        Long orderId =
                Long.valueOf(ctx.getActionContext("orderId").toString());

        orderMapper.deleteById(orderId);
        //回滚完成后，删除标记
        ResultHolder.removeResult(OrderTccAction.class,ctx.getXid());
        return true;
    }
}
