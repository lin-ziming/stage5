package cn.tedu.order.controller;

import cn.tedu.order.entity.Order;
import cn.tedu.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Haitao
 * @date 2020/12/9 15:01
 */
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/create")
    public String create(Order order){
        orderService.create(order);
        return "创建订单完成";
    }
}
