package cn.tedu.account.service;

import java.math.BigDecimal;

/**
 * @author Haitao
 * @date 2020/12/9 11:29
 */
public interface AccountService {
    void decrease(Long userId, BigDecimal money);
}
