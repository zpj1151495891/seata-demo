package com.example.teststorageservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.testcommonservice.dto.CommodityDTO;
import com.example.teststorageservice.dao.TStorage;
import net.trueland.tcloud.scrm.common.model.Rsp;

/**
*
*/
public interface TStorageService extends IService<TStorage> {

    /**
     * 扣减库存
     */
    Rsp decreaseStorage(CommodityDTO commodityDTO);

}
