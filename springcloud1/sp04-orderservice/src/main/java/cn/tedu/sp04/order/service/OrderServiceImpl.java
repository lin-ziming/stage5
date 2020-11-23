package cn.tedu.sp04.order.service;

import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Haitao
 * @date 2020/11/23 15:22
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public Order getOrder(String orderId) {
        //获取订单时，要通过调用远程服务，来获取商品列表和用户信息
        //TODO: 调用user-service获取用户信息
        //TODO: 调用item-service获取商品信息

        log.info("获取订单： orderId="+orderId);
        Order order = new Order(orderId,null,null);
        return order;
    }

    @Override
    public void addOrder(Order order) {
        //TODO: 调用item-service减少商品库存
        //TODO: 调用user-service增加用户积分
        log.info("保存订单："+order);
    }
}
