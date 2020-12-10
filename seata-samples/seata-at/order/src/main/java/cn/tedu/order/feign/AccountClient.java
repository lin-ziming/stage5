package cn.tedu.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @author Haitao
 * @date 2020/12/9 16:38
 */
@FeignClient(name = "account")
public interface AccountClient {
    @GetMapping("/decrease")
    String decrease(@RequestParam Long userId, @RequestParam BigDecimal money);
}
