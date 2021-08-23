package com.example.testaccountservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.testaccountservice.dao.TAccount;
import org.apache.ibatis.annotations.Param;

/**
* @Entity com.example.testaccountservice.dao.TAccount
*/
public interface TAccountMapper extends BaseMapper<TAccount> {

    /**
     * 减少账户余额
     * @param userId
     * @param amount
     * @return
     */
    int decreaseAccount(@Param("userId") String userId, @Param("amount") Double amount);
}
