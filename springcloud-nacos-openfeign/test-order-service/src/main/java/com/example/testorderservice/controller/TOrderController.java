package com.example.testorderservice.controller;

import com.example.testcommonservice.dto.OrderDTO;
import com.example.testorderservice.service.TOrderService;
import lombok.extern.slf4j.Slf4j;
import net.trueland.tcloud.scrm.common.model.Rsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  订单服务
 * </p>
 *
 * * @author lidong
 * @since 2019-09-04
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class TOrderController {


    @Autowired
    private TOrderService orderService;

    @PostMapping("/create_order")
    Rsp<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO){
        log.info("请求订单微服务：{}",orderDTO.toString());
        return orderService.createOrder(orderDTO);
    }
}

