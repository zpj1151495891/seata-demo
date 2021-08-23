package com.example.testcommonservice.feign;

import com.alibaba.nacos.common.model.RestResult;
import com.example.testcommonservice.dto.CommodityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author: lidong
 * @Description  库存服务
 * @Date Created in 2019/9/5 16:22
 */
@FeignClient(value = "http://cloud-storage-example",path = "storage")
public interface StorageFeignService {

    /**
     * 扣减库存
     */
    @PostMapping(value = "dec_storage", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    RestResult decreaseStorage(CommodityDTO commodityDTO);
}
