package com.example.teststorageservice.service;

import com.alibaba.nacos.common.model.RestResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.testcommonservice.dto.CommodityDTO;
import com.example.teststorageservice.dao.TStorage;

/**
*
*/
public interface TStorageService extends IService<TStorage> {

    /**
     * 扣减库存
     */
    RestResult decreaseStorage(CommodityDTO commodityDTO);

}
