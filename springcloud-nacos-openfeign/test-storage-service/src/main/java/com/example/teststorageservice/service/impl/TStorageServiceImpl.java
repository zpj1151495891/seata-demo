package com.example.teststorageservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.testcommonservice.dto.CommodityDTO;
import com.example.teststorageservice.dao.TStorage;
import com.example.teststorageservice.service.TStorageService;
import com.example.teststorageservice.mapper.TStorageMapper;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import net.trueland.tcloud.scrm.common.exception.model.SysResultCode;
import net.trueland.tcloud.scrm.common.model.Rsp;
import org.springframework.stereotype.Service;

/**
*
*/
@Service
@Slf4j
public class TStorageServiceImpl extends ServiceImpl<TStorageMapper, TStorage>
implements TStorageService{

    @Override
    public Rsp decreaseStorage(CommodityDTO commodityDTO) {
        log.info("全局事务id:{}", RootContext.getXID());

        int storage = baseMapper.decreaseStorage(commodityDTO.getCommodityCode(), commodityDTO.getCount());

        if (storage > 0){
            return Rsp.success();
        }

        return Rsp.fail(SysResultCode.OPERATION_FAIL);
    }
}
