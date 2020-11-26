package cn.tedu.sp04.feign;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.web.util.JsonResult;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author Haitao
 * @date 2020/11/26 10:56
 */
@Component
public class ItemClientFB implements ItemClient{

    @Override
    public JsonResult<List<Item>> getItems(String orderId) {
        //模拟有redis缓存数据，调用商品失败，从redis取出缓存发送给客户端
        if (Math.random()<0.5){ //50%的概率返回缓存数据
            return JsonResult.ok().data(
                    Arrays.asList(new Item[]{
                            new Item(1,"商品1",2),
                            new Item(2,"商品2",1),
                            new Item(3,"商品3",4),
                            new Item(4,"商品4",6),
                            new Item(5,"商品5",2),
                    })
            );
        }
        return JsonResult.err().msg("获取商品列表失败");
    }

    @Override
    public JsonResult<?> decreaseNumber(List<Item> items) {
        return JsonResult.err().msg("减少商品库存失败");
    }
}
