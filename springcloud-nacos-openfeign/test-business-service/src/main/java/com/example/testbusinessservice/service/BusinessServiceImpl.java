package com.example.testbusinessservice.service;

import com.alibaba.nacos.common.model.RestResult;
import com.alibaba.nacos.common.model.RestResultUtils;
import com.example.testcommonservice.dto.BusinessDTO;
import com.example.testcommonservice.dto.CommodityDTO;
import com.example.testcommonservice.dto.OrderDTO;
import com.example.testcommonservice.feign.OrderFeignService;
import com.example.testcommonservice.feign.StorageFeignService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: lidong
 * @Description  Dubbo业务发起方逻辑
 * @Date Created in 2019/9/5 18:36
 */
@Service
@Slf4j
public class BusinessServiceImpl implements BusinessService{

    @Autowired
    private StorageFeignService storageFeignService;

    @Autowired
    private OrderFeignService orderFeignService;

    boolean flag = false;

    /**
     * 处理业务逻辑 正常的业务逻辑
     * @Param:
     * @Return:
     */
    @GlobalTransactional(timeoutMills = 300000, name = "dubbo-gts-seata-example")
    @Override
    public RestResult handleBusiness(BusinessDTO businessDTO) {
        log.info("开始全局事务，XID = " + RootContext.getXID());
        //1、扣减库存
        CommodityDTO commodityDTO = new CommodityDTO();
        commodityDTO.setCommodityCode(businessDTO.getCommodityCode());
        commodityDTO.setCount(businessDTO.getCount());
        RestResult storageResponse = storageFeignService.decreaseStorage(commodityDTO);
        //2、创建订单
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserId(businessDTO.getUserId());
        orderDTO.setCommodityCode(businessDTO.getCommodityCode());
        orderDTO.setOrderCount(businessDTO.getCount());
        orderDTO.setOrderAmount(businessDTO.getAmount());
        RestResult<OrderDTO> response = orderFeignService.createOrder(orderDTO);

        if (!storageResponse.ok() || !response.ok()) {
            throw new RuntimeException("扣减库存或生成订单失败");
        }

        return RestResultUtils.success(response.getData());

    }

    /**
     * 出处理业务服务，出现异常回顾
     *
     * @param businessDTO
     * @return
     */
    @GlobalTransactional(timeoutMills = 300000, name = "dubbo-gts-seata-example")
    @Override
    public RestResult handleBusiness2(BusinessDTO businessDTO) {
        log.info("开始全局事务，XID = " + RootContext.getXID());
        //1、扣减库存
        CommodityDTO commodityDTO = new CommodityDTO();
        commodityDTO.setCommodityCode(businessDTO.getCommodityCode());
        commodityDTO.setCount(businessDTO.getCount());
        RestResult storageResponse = storageFeignService.decreaseStorage(commodityDTO);
        //2、创建订单
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserId(businessDTO.getUserId());
        orderDTO.setCommodityCode(businessDTO.getCommodityCode());
        orderDTO.setOrderCount(businessDTO.getCount());
        orderDTO.setOrderAmount(businessDTO.getAmount());
        RestResult<OrderDTO> response = orderFeignService.createOrder(orderDTO);

//        打开注释测试事务发生异常后，全局回滚功能
        if (!flag) {
            throw new RuntimeException("测试抛异常后，分布式事务回滚！");
        }

        if (!storageResponse.ok() || !response.ok()) {
            throw new RuntimeException("扣减库存或生成订单失败");
        }

        return RestResultUtils.success(response.getData());
    }
}
