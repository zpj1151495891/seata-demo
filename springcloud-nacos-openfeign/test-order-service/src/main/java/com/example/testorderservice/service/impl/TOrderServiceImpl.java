package com.example.testorderservice.service.impl;

import com.alibaba.nacos.common.model.RestResult;
import com.alibaba.nacos.common.model.RestResultUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.testcommonservice.dto.AccountDTO;
import com.example.testcommonservice.dto.OrderDTO;
import com.example.testcommonservice.feign.AccountFeignService;
import com.example.testorderservice.dao.TOrder;
import com.example.testorderservice.service.TOrderService;
import com.example.testorderservice.mapper.TOrderMapper;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 *
 */
@Service
@Slf4j
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder>
        implements TOrderService {

    @Autowired
    private AccountFeignService accountFeignService;

    @Override
    public RestResult<OrderDTO> createOrder(OrderDTO orderDTO) {
        log.info("全局事务id:{}", RootContext.getXID());

        //扣减用户账户
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUserId(orderDTO.getUserId());
        accountDTO.setAmount(orderDTO.getOrderAmount());
        RestResult objectResponse = accountFeignService.decreaseAccount(accountDTO);

        //生成订单号
        orderDTO.setOrderNo(UUID.randomUUID().toString().replace("-", ""));
        //生成订单
        TOrder tOrder = new TOrder();
        BeanUtils.copyProperties(orderDTO, tOrder);
        tOrder.setCount(orderDTO.getOrderCount());
        tOrder.setAmount(orderDTO.getOrderAmount().doubleValue());
        try {
            baseMapper.createOrder(tOrder);
        } catch (Exception e) {
            return RestResultUtils.failed(e.getMessage());
        }

        if (!objectResponse.ok()) {
            return RestResultUtils.failed("扣款失败");
        }

        return RestResultUtils.success();
    }
}
