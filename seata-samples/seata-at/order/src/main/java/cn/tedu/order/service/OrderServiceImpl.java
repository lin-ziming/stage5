package cn.tedu.order.service;

import cn.tedu.order.entity.Order;
import cn.tedu.order.feign.AccountClient;
import cn.tedu.order.feign.EasyIdClient;
import cn.tedu.order.feign.StorageClient;
import cn.tedu.order.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author Haitao
 * @date 2020/12/9 14:52
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    EasyIdClient easyIdClient;

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private StorageClient storageClient;

    @Override
    public void create(Order order) {
        //临时随机产生一个id
//        Long orderId = Math.abs(new Random().nextLong());

        // 远程调用 全局唯一id发号器，获得id
        Long orderId = Long.valueOf(easyIdClient.nextId("order_business"));
        order.setId(orderId);

        orderMapper.create(order);

        // 远程调用库存，减少库存
        storageClient.decrease(order.getUserId(),order.getCount());
        // 远程调用账户，扣减金额
        accountClient.decrease(order.getUserId(),order.getMoney());
    }
}
