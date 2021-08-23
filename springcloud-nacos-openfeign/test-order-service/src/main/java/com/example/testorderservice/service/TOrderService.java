package com.example.testorderservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.testcommonservice.dto.OrderDTO;
import com.example.testorderservice.dao.TOrder;
import net.trueland.tcloud.scrm.common.model.Rsp;

/**
*
*/
public interface TOrderService extends IService<TOrder> {

    /**
     * 创建订单
     */
    Rsp<OrderDTO> createOrder(OrderDTO orderDTO);

}
