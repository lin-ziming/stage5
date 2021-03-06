package cn.tedu.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Haitao
 * @date 2020/12/9 16:31
 */
@FeignClient(name = "EASY-ID-GENERATOR")
public interface EasyIdClient {
    @GetMapping("/segment/ids/next_id")
    String nextId(@RequestParam String businessType);
}
