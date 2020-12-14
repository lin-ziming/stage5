package cn.tedu.account.tcc;

import cn.tedu.account.entity.Account;
import cn.tedu.account.mapper.AccountMapper;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountTccActionImpl implements AccountTccAction {
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public boolean prepareDecreaseAccount(BusinessActionContext ctx, Long userId, BigDecimal money) {
//        if (Math.random() < 0.5){
//            throw new RuntimeException("模拟异常");
//        }

        Account a = accountMapper.selectById(userId);
        if (a == null || a.getResidue().compareTo(money) < 0){
            throw new RuntimeException("账户金额不足");
        }
        accountMapper.updateFrozen(
                userId,
                a.getResidue().subtract(money),
                a.getFrozen().add(money));

        //第一阶段的成功标记
        ResultHolder.setResult(AccountTccAction.class,ctx.getXid(),"p");
        return true;
    }

    @Override
    public boolean commit(BusinessActionContext ctx) {
        String p = ResultHolder.getResult(AccountTccAction.class, ctx.getXid());
        if (p == null){
            return true;
        }

        Long userId = Long.valueOf(ctx.getActionContext("userId").toString());
        BigDecimal money = new BigDecimal(ctx.getActionContext("money").toString());

        accountMapper.updateFrozenToUsed(userId,money);
        ResultHolder.removeResult(AccountTccAction.class,ctx.getXid());
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext ctx) {
        String p = ResultHolder.getResult(AccountTccAction.class, ctx.getXid());
        if (p == null){
            return true;
        }

        Long userId = Long.valueOf(ctx.getActionContext("userId").toString());
        BigDecimal money = new BigDecimal(ctx.getActionContext("money").toString());

        accountMapper.updateFrozenToResidue(userId,money);
        ResultHolder.removeResult(AccountTccAction.class,ctx.getXid());
        return true;
    }
}
