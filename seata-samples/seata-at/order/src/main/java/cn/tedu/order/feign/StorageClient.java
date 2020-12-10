package cn.tedu.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Haitao
 * @date 2020/12/9 16:33
 */
@FeignClient(name = "storage")
public interface StorageClient {
    @GetMapping("/decrease")
    String decrease(@RequestParam Long productId,@RequestParam Integer count);
}
