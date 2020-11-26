package cn.tedu.sp09.feign;

import cn.tedu.sp01.pojo.User;
import cn.tedu.web.util.JsonResult;
import org.springframework.stereotype.Component;

/**
 * @author Haitao
 * @date 2020/11/26 9:32
 */
@Component
public class UserClientFB implements UserClient{
    @Override
    public JsonResult<User> getUser(Integer userId) {
        return JsonResult.err().msg("获取用户失败");
    }

    @Override
    public JsonResult<?> addScore(Integer userId, Integer score) {
        return JsonResult.err().msg("增加用户积分失败");
    }
}
