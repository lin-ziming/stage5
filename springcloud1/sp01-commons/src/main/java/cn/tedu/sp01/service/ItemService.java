package cn.tedu.sp01.service;

import cn.tedu.sp01.pojo.Item;

import java.util.List;

/**
 * @author Haitao
 * @date 2020/11/23 10:34
 */
public interface ItemService {
    //获取订单中的商品列表
    List<Item> getItems(String orderId);

    //减少库存
    void decrease(List<Item> list);
}
