package com.example.teststorageservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.teststorageservice.dao.TStorage;
import org.apache.ibatis.annotations.Param;

/**
* @Entity com.example.teststorageservice.dao.TStorage
*/
public interface TStorageMapper extends BaseMapper<TStorage> {

    /**
     * 扣减商品库存
     * @Param: commodityCode 商品code  count扣减数量
     * @Return:
     */
    int decreaseStorage(@Param("commodityCode") String commodityCode, @Param("count") Integer count);

}
