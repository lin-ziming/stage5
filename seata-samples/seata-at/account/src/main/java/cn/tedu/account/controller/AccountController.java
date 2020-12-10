package cn.tedu.account.controller;

import cn.tedu.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author Haitao
 * @date 2020/12/9 11:34
 */
@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/decrease")
    public String decrease(Long userId, BigDecimal money){
        accountService.decrease(userId,money);
        return "扣减账户金额完成";
    }
}
