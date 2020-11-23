package cn.tedu.sp01.service;

import cn.tedu.sp01.pojo.Order;

/**
 * @author Haitao
 * @date 2020/11/23 10:39
 */
public interface OrderService {
    //获取订单
    Order getOrder(String orderId);
    //保存订单
    void addOrder(Order order);
}
