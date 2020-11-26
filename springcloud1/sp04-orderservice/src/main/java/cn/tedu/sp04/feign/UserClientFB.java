package cn.tedu.sp04.feign;

import cn.tedu.sp01.pojo.User;
import cn.tedu.web.util.JsonResult;
import org.springframework.stereotype.Component;

/**
 * @author Haitao
 * @date 2020/11/26 10:55
 */
@Component
public class UserClientFB implements UserClient{

    @Override
    public JsonResult<User> getUser(Integer userId) {
        if(Math.random()<0.5){ //50%的概率返回模拟缓存数据
            return JsonResult.ok().data(new User(userId,"缓存name"+userId,"缓存pwd"+userId));
        }
        return JsonResult.err().msg("获取用户失败");
    }

    @Override
    public JsonResult<?> addScore(Integer userId, Integer score) {
        return JsonResult.err().msg("增加积分失败");
    }
}
