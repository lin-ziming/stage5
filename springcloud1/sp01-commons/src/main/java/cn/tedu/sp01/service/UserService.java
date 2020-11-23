package cn.tedu.sp01.service;

import cn.tedu.sp01.pojo.User;

/**
 * @author Haitao
 * @date 2020/11/23 10:37
 */
public interface UserService {
    //获取用户
    User getUser(Integer userId);
    //增加积分
    void addScore(Integer userId,Integer score);
}
