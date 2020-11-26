package cn.tedu.sp04.feign;

import cn.tedu.sp01.pojo.User;
import cn.tedu.web.util.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Haitao
 * @date 2020/11/26 10:51
 */
@FeignClient(name = "user-service",fallback = UserClientFB.class)
public interface UserClient {
    @GetMapping("/{userId}")
    JsonResult<User> getUser(@PathVariable Integer userId);

    @GetMapping("/{userId}/score")
    JsonResult<?> addScore(@PathVariable Integer userId,@RequestParam Integer score);
}
