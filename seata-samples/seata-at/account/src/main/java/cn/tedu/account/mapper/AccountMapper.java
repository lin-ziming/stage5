package cn.tedu.account.mapper;

import cn.tedu.account.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.math.BigDecimal;

/**
 * @author Haitao
 * @date 2020/12/9 11:05
 */
public interface AccountMapper extends BaseMapper<Account> {
    void decrease(Long userId, BigDecimal money);
}
