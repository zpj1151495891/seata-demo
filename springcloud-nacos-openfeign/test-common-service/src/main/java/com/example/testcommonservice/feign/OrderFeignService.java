package com.example.testcommonservice.feign;

import com.alibaba.nacos.common.model.RestResult;
import com.example.testcommonservice.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author: lidong
 * @Description  订单服务接口
 * @Date Created in 2019/9/5 16:28
 */
@FeignClient(value = "http://cloud-order-example",path = "order")
public interface OrderFeignService {

    /**
     * 创建订单
     */
    @PostMapping(value = "create_order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    RestResult<OrderDTO> createOrder(OrderDTO orderDTO);
}
