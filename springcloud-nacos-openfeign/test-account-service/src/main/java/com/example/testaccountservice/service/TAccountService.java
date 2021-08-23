package com.example.testaccountservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.testaccountservice.dao.TAccount;
import com.example.testcommonservice.dto.AccountDTO;
import net.trueland.tcloud.scrm.common.model.Rsp;

/**
*
*/
public interface TAccountService extends IService<TAccount> {

    Rsp decreaseAccount(AccountDTO accountDTO);

}
