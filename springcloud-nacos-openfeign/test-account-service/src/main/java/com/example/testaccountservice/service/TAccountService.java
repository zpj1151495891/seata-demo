package com.example.testaccountservice.service;

import com.alibaba.nacos.common.model.RestResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.testaccountservice.dao.TAccount;
import com.example.testcommonservice.dto.AccountDTO;
import feign.Response;

/**
*
*/
public interface TAccountService extends IService<TAccount> {

    RestResult decreaseAccount(AccountDTO accountDTO);

}
