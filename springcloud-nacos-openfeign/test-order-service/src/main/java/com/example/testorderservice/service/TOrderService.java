package com.example.testorderservice.service;

import com.alibaba.nacos.common.model.RestResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.testcommonservice.dto.OrderDTO;
import com.example.testorderservice.dao.TOrder;

/**
*
*/
public interface TOrderService extends IService<TOrder> {

    /**
     * 创建订单
     */
    RestResult<OrderDTO> createOrder(OrderDTO orderDTO);

}
