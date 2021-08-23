package com.example.testcommonservice.feign;

import com.alibaba.nacos.common.model.RestResult;
import com.example.testcommonservice.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author: lidong
 * @Description  账户服务接口
 * @Date Created in 2019/9/5 16:37
 */
@FeignClient(value = "http://cloud-account-example",path = "account")
public interface AccountFeignService {

    /**
     * 从账户扣钱
     */
    @PostMapping(value = "dec_account", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    RestResult decreaseAccount(AccountDTO accountDTO);
}
