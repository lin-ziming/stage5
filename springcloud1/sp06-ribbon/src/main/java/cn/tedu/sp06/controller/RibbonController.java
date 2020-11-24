package cn.tedu.sp06.controller;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Haitao
 * @date 2020/11/24 15:06
 */
@RestController
@Slf4j
public class RibbonController {
    @Autowired
    private RestTemplate rt;

    /**
     * 使用远程调用工具RestTemplate，远程调用商品服务
     * http://localhost:8001/{orderId}
     * {1} - RestTemplate 定义的一种占位符格式
     * @param orderId
     * @return
     */
    @GetMapping("/item-service/{orderId}")
    public JsonResult<List<Item>> getItems(@PathVariable String orderId){
        return rt.getForObject("http://item-service/{1}",JsonResult.class,orderId);
    }

    @PostMapping("/item-service/decreaseNumber")
    public JsonResult decreaseNumber(@RequestBody List<Item> items){
        return rt.postForObject("http://item-service:8001/decreaseNumber",items,JsonResult.class);
    }

    //---------------------------------------------

    @GetMapping("/user-service/{userId}")
    public JsonResult<User> getUser(@PathVariable Integer userId){
        return rt.getForObject("http://user-service:8101/{1}",JsonResult.class,userId);
    }
    @GetMapping("/user-service/{userId}/score")
    public JsonResult<?> addScore(@PathVariable Integer userId,Integer score){
        return rt.getForObject("http://user-service:8101/{1}/score?score={2}",JsonResult.class,userId,score);
    }

    @GetMapping("/order-service/{orderId}")
    public JsonResult<Order> getOrder(@PathVariable String orderId){
        return rt.getForObject("http://order-service:8201/{1}",JsonResult.class,orderId);
    }
    @GetMapping("/order-service")
    public JsonResult<?> addOrder(){
        return rt.getForObject("http://order-service:8201/",JsonResult.class);
    }
}
