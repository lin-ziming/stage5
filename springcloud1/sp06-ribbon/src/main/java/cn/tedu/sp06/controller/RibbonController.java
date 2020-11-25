package cn.tedu.sp06.controller;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.web.util.JsonResult;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
     * @HystrixCommand(fallbackMethod = "getItemsFB")
     * 当调用远程服务失败，跳转到指定的方法执行降级代码
     *
     * @param orderId
     * @return
     */
    @HystrixCommand(fallbackMethod = "getItemsFB")
    @GetMapping("/item-service/{orderId}")
    public JsonResult<List<Item>> getItems(@PathVariable String orderId){
        /**
         * 使用远程调用工具RestTemplate，远程调用商品服务
         * http://localhost:8001/{orderId}
         * {1} - RestTemplate 定义的一种占位符格式
         */
        return rt.getForObject("http://item-service/{1}",JsonResult.class,orderId);
    }

    @HystrixCommand(fallbackMethod = "decreaseNumberFB")
    @PostMapping("/item-service/decreaseNumber")
    public JsonResult decreaseNumber(@RequestBody List<Item> items){
        return rt.postForObject("http://item-service/decreaseNumber",items,JsonResult.class);
    }

    //---------------------------------------------

    @HystrixCommand(fallbackMethod = "getUserFB")
    @GetMapping("/user-service/{userId}")
    public JsonResult<User> getUser(@PathVariable Integer userId){
        return rt.getForObject("http://user-service/{1}",JsonResult.class,userId);
    }
    @HystrixCommand(fallbackMethod = "addScoreFB")
    @GetMapping("/user-service/{userId}/score")
    public JsonResult<?> addScore(@PathVariable Integer userId,Integer score){
        return rt.getForObject("http://user-service/{1}/score?score={2}",JsonResult.class,userId,score);
    }

    @HystrixCommand(fallbackMethod = "getOrderFB")
    @GetMapping("/order-service/{orderId}")
    public JsonResult<Order> getOrder(@PathVariable String orderId){
        return rt.getForObject("http://order-service/{1}",JsonResult.class,orderId);
    }
    @HystrixCommand(fallbackMethod = "addOrderFB")
    @GetMapping("/order-service")
    public JsonResult<?> addOrder(){
        return rt.getForObject("http://order-service/",JsonResult.class);
    }


    //-------------------------------------------------------
    /**添加降级代码*/

    public JsonResult<List<Item>> getItemsFB(String orderId){
        return JsonResult.err().msg("获取订单的商品列表失败");
    }
    public JsonResult decreaseNumberFB(List<Item> items){
        return JsonResult.err().msg("减少商品库存失败");
    }
    public JsonResult<User> getUserFB(Integer userId){
        return JsonResult.err().msg("获取用户失败");
    }
    public JsonResult<?> addScoreFB(Integer userId,Integer score){
        return JsonResult.err().msg("增加积分失败");
    }
    public JsonResult<Order> getOrderFB(String orderId){
        return JsonResult.err().msg("获取订单失败");
    }
    public JsonResult<?> addOrderFB(){
        return JsonResult.err().msg("添加订单失败");
    }


}
