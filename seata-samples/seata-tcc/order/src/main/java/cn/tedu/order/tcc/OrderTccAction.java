package cn.tedu.order.tcc;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

import java.math.BigDecimal;

/**
 * 顶单的 TCC 操作接口
 * @author Haitao
 * @date 2020/12/14 15:59
 */
@LocalTCC
public interface OrderTccAction {
    //Try - 预留资源，冻结订单
    @TwoPhaseBusinessAction(name = "OrderTccAction")
    public boolean prepareCreateOrder(
            BusinessActionContext ctx,
            @BusinessActionContextParameter(paramName = "orderId") Long orderId,
            @BusinessActionContextParameter(paramName = "userId") Long userId,
            @BusinessActionContextParameter(paramName = "productId")Long productId,
            @BusinessActionContextParameter(paramName = "count")Integer count,
            @BusinessActionContextParameter(paramName = "money")BigDecimal money);

    //Confirm - 确认，提交
    public boolean commit(BusinessActionContext ctx);

    //Cancel - 取消，回滚
    public boolean rollback(BusinessActionContext ctx);
}
