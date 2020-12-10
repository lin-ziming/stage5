package cn.tedu.storage.service;

import cn.tedu.storage.mapper.StorageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Haitao
 * @date 2020/12/9 14:14
 */
@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    private StorageMapper storageMapper;
    @Override
    public void decrease(Long productId, Integer count) {
        storageMapper.decrease(productId,count);
    }
}
