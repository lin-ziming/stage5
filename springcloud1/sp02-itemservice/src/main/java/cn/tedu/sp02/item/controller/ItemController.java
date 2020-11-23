package cn.tedu.sp02.item.controller;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.service.ItemService;
import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Haitao
 * @date 2020/11/23 11:32
 */
@Slf4j
@RestController
public class ItemController {
    @Autowired
    private ItemService itemService;

    //配置文件 application.yml 中的 server.port=8001 注入到这个变量
    //是为了后面负载均衡测试，可以直接看到调用的是哪个服务器
    @Value("${server.port}")
    private int port;

    //获取订单的商品列表
    @GetMapping("/{orderId}")
    public JsonResult<List<Item>> getItems(@PathVariable String orderId){
        log.info("获取商品列表，orderId="+orderId+",port="+port);

        List<Item> items = itemService.getItems(orderId);
        return JsonResult.ok().data(items).msg("port="+port);
    }
    /**
     * 减少商品库存
     * @RequestBody 完整接收请求协议体中的数据
     * post请求才有协议体，get请求没有
     */
    @PostMapping("/decreaseNumber")
    public JsonResult<?> decreaseNumber(@RequestBody List<Item> items){
        for (Item item : items){
            log.info("减少商品库存："+item);
        }
        itemService.decrease(items);
        return JsonResult.ok();
    }
}
