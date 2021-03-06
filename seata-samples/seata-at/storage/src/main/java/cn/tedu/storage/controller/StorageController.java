package cn.tedu.storage.controller;

import cn.tedu.storage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Haitao
 * @date 2020/12/9 14:18
 */
@RestController
public class StorageController {
    @Autowired
    private StorageService storageService;

    @GetMapping("/decrease")
    public String decrease(Long productId,Integer count){
        storageService.decrease(productId,count);
        return "减少商品库存完成";
    }
}
