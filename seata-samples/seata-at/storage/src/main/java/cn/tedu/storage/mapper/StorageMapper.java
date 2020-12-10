package cn.tedu.storage.mapper;

import cn.tedu.storage.entity.Storage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author Haitao
 * @date 2020/12/9 14:06
 */
public interface StorageMapper extends BaseMapper<Storage> {
    void decrease(Long productId, Integer count);
}
