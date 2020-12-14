package cn.tedu.storage.tcc;

import cn.tedu.storage.entity.Storage;
import cn.tedu.storage.mapper.StorageMapper;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author Haitao
 * @date 2020/12/14 17:36
 */
@Component
public class StorageTccActionImpl implements StorageTccAction {
    @Autowired
    private StorageMapper storageMapper;

    @Override
    public boolean prepareDecreaseStorage(BusinessActionContext ctx, Long productId, Integer count) {
        // 判断是否有足够库存
        Storage s = storageMapper.selectById(productId);
        if (s==null || s.getResidue()<count){
            throw new RuntimeException("库存不足");
        }

        storageMapper.updateFrozen(
                productId,s.getResidue()-count,s.getFrozen()+count);

        // 第一阶段完成后，保存一个标记
        ResultHolder.setResult(StorageTccAction.class,ctx.getXid(),"p");
        return true;
    }

    @Override
    public boolean commit(BusinessActionContext ctx) {
        String p = ResultHolder.getResult(StorageTccAction.class, ctx.getXid());
        if (p==null){
            return true;
        }
        Long productId = Long.valueOf(ctx.getActionContext("productId").toString());
        Integer count = Integer.valueOf(ctx.getActionContext("count").toString());

        storageMapper.updateFrozenToUsed(productId,count);
        ResultHolder.removeResult(StorageTccAction.class,ctx.getXid());
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext ctx) {
        String p = ResultHolder.getResult(StorageTccAction.class, ctx.getXid());
        if (p==null){
            return true;
        }
        Long productId = Long.valueOf(ctx.getActionContext("productId").toString());
        Integer count = Integer.valueOf(ctx.getActionContext("count").toString());

        storageMapper.updateFrozenToResidue(productId,count);
        ResultHolder.removeResult(StorageTccAction.class,ctx.getXid());
        return true;
    }
}
