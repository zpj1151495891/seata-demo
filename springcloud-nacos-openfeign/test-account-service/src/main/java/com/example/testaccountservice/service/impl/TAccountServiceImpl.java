package com.example.testaccountservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.testaccountservice.dao.TAccount;
import com.example.testaccountservice.service.TAccountService;
import com.example.testaccountservice.mapper.TAccountMapper;
import com.example.testcommonservice.dto.AccountDTO;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import net.trueland.tcloud.scrm.common.exception.model.SysResultCode;
import net.trueland.tcloud.scrm.common.model.Rsp;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
@Slf4j
public class TAccountServiceImpl extends ServiceImpl<TAccountMapper, TAccount>
        implements TAccountService {

    @Override
    public Rsp decreaseAccount(AccountDTO accountDTO) {
        log.info("全局事务id:{}", RootContext.getXID());

        int account = getBaseMapper().decreaseAccount(accountDTO.getUserId(), accountDTO.getAmount().doubleValue());

        if (account > 0){
            return Rsp.success();
        }

        return Rsp.fail(SysResultCode.OPERATION_FAIL);
    }

}
