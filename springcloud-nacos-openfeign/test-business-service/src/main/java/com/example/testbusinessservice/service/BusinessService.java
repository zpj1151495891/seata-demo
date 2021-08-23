package com.example.testbusinessservice.service;


import com.example.testcommonservice.dto.BusinessDTO;
import net.trueland.tcloud.scrm.common.model.Rsp;

/**
 * @Author: lidong
 * @Description
 * @Date Created in 2019/9/5 17:17
 */
public interface BusinessService {

    /**
     * 出处理业务服务
      * @param businessDTO
     * @return
     */
    Rsp handleBusiness(BusinessDTO businessDTO);


    /**
     * 出处理业务服务，出现异常回顾
     * @param businessDTO
     * @return
     */
    Rsp handleBusiness2(BusinessDTO businessDTO);
}
