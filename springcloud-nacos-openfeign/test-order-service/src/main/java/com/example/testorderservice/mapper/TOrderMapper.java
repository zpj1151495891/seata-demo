package com.example.testorderservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.testorderservice.dao.TOrder;
import org.apache.ibatis.annotations.Param;

/**
* @Entity com.example.testorderservice.dao.TOrder
*/
public interface TOrderMapper extends BaseMapper<TOrder> {


    /**
     * 创建订单
     * @Param:  order 订单信息
     * @Return:
     */
    void createOrder(@Param("order") TOrder order);
}
