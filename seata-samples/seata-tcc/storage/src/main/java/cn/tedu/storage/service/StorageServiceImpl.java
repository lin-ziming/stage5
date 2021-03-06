package cn.tedu.storage.service;

import cn.tedu.storage.mapper.StorageMapper;
import cn.tedu.storage.tcc.StorageTccAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    private StorageMapper storageMapper;
    @Autowired
    private StorageTccAction storageTccAction;

    @Transactional
    @Override
    public void decrease(Long productId, Integer count) throws Exception {
//        storageMapper.decrease(productId,count);

        storageTccAction.prepareDecreaseStorage(
                null, productId, count);

    }

}
