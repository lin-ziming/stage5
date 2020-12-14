package cn.tedu.storage.tcc;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @author Haitao
 * @date 2020/12/14 17:31
 */
@LocalTCC
public interface StorageTccAction {
    @TwoPhaseBusinessAction(name = "StorageTccAction")
    boolean prepareDecreaseStorage(
            BusinessActionContext ctx,
            @BusinessActionContextParameter(paramName = "productId") Long productId,
            @BusinessActionContextParameter(paramName = "count") Integer count);

    boolean commit(BusinessActionContext ctx);
    boolean rollback(BusinessActionContext ctx);
}
