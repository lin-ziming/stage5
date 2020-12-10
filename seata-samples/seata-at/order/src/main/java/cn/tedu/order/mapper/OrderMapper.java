package cn.tedu.order.mapper;

import cn.tedu.order.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author Haitao
 * @date 2020/12/9 14:45
 */
public interface OrderMapper extends BaseMapper<Order> {
    void create(Order order);
}
