package cn.tedu.sp04.order.service;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.sp01.service.OrderService;
import cn.tedu.sp04.feign.ItemClient;
import cn.tedu.sp04.feign.UserClient;
import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ItemClient itemClient;
    @Autowired
    private UserClient userClient;

    @Override
    public Order getOrder(String orderId) {
        //获取订单时，要通过调用远程服务，来获得商品列表和用户信息
        //远程调用item-service，获得这个订单的商品列表
        JsonResult<List<Item>> items = itemClient.getItems(orderId);
        //远程调用user-service，获得当前用户的用户信息
        JsonResult<User> user = userClient.getUser(8);//真实项目中应该获取已登录的用户id
        log.info("获取订单： orderId="+orderId);
        Order order = new Order(orderId, user.getData(), items.getData());
        return order;
    }

    @Override
    public void addOrder(Order order) {
        log.info("保存订单： "+order);
        //远程调用item-service, 减少商品库存
        itemClient.decreaseNumber(order.getItems());
        //远程调用user-service, 增加用户积分
        userClient.addScore(order.getUser().getId(), 1000);
    }
}
