package com.smart.program.request.account.impl;

import com.smart.program.common.ErrorConstant;
import com.smart.program.domain.account.AccountEntity;
import com.smart.program.domain.account.AccountWaterEntity;
import com.smart.program.domain.recharge.RechargeOrderEntity;
import com.smart.program.exception.BusinessException;
import com.smart.program.idwork.IdWorker;
import com.smart.program.repository.account.AccountDao;
import com.smart.program.repository.account.AccountWaterDao;
import com.smart.program.repository.recharge.RechargeOrderRepository;
import com.smart.program.request.UserRequest;
import com.smart.program.request.account.UserAccountService;
import com.smart.program.response.account.UserAccountResponse;
import com.smart.program.response.account.UserAccountWaterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RechargeOrderRepository rechargeOrderRepository;

    @Autowired
    private AccountWaterDao accountWaterDao;

    /**
     * 修改用户现金账户信息
     *
     * @param orderId 用户主键
     * @throws Exception
     */
    public void updateUserAccount(Long orderId) throws Exception {
        RechargeOrderEntity rechargeOrderEntity = rechargeOrderRepository.queryOrderById(orderId);
        if (rechargeOrderEntity == null) {
            throw new BusinessException(ErrorConstant.RECHARGE_ORDER_IS_NULL_ERROR, ErrorConstant.RECHARGE_ORDER_IS_NULL_ERROR_MSG);
        }
        BigDecimal changeAmount = rechargeOrderEntity.getRechargeMoney();
        AccountEntity accountEntity = accountDao.queryUserAccountByUserId(rechargeOrderEntity.getUserId());
        if (null == accountEntity) {
            accountEntity = new AccountEntity();
            accountEntity.setId(idWorker.nextId());
            accountEntity.setUserId(rechargeOrderEntity.getUserId());
            accountEntity.setAccountNum(changeAmount);
        } else {
            accountEntity.setAccountNum(accountEntity.getAccountNum().add(changeAmount));
        }
        accountDao.saveAndFlush(accountEntity);

        insertUserAccountWater(rechargeOrderEntity.getUserId(), (byte) 1, changeAmount, orderId);
    }

    /**
     * 增加交易流水信息
     *
     * @param userId    用户主键
     * @param waterType 流水类型：1 充值, 0消费,2代金券
     * @param waterNum  交易金额
     * @throws Exception
     */
    private void insertUserAccountWater(String userId, byte waterType, BigDecimal waterNum, Long orderId) throws Exception {
        AccountWaterEntity accountWaterEntity = new AccountWaterEntity();
        accountWaterEntity.setWaterId(idWorker.nextId());
        accountWaterEntity.setWaterType(waterType);
        accountWaterEntity.setUserId(userId);
        accountWaterEntity.setOrderId(orderId);
    }

    /**
     * 获取用户现金账户余额
     *
     * @param request
     * @return
     * @throws Exception
     */
    public UserAccountResponse queryUserAccount(UserRequest request) throws Exception {
        AccountEntity accountEntity = accountDao.queryUserAccountByUserId(request.getUserId());
        UserAccountResponse userAccountResponse = new UserAccountResponse();
        if (accountEntity == null) {
            userAccountResponse.setAmount(BigDecimal.ZERO);
        } else {
            userAccountResponse.setAmount(accountEntity.getAccountNum());
        }
        return userAccountResponse;
    }

    /**
     * 查询用户现金账户流水信息
     *
     * @return
     * @throws Exception
     */
    public List<UserAccountWaterResponse> queryUserAccountWater(UserRequest request) throws Exception {
        List<UserAccountWaterResponse> waterResponses = new ArrayList<>();
        // 分页获取流水信息
        List<AccountWaterEntity> accountWaterEntities = accountWaterDao.queryUserAccountWaterByUserId(request);
        for (AccountWaterEntity accountWaterEntity : accountWaterEntities) {
            UserAccountWaterResponse userAccountWaterResponse = new UserAccountWaterResponse();
            userAccountWaterResponse.setWaterType(accountWaterEntity.getWaterType());
            userAccountWaterResponse.setWaterNum(accountWaterEntity.getWaterNum());
            userAccountWaterResponse.setWaterTime(accountWaterEntity.getWanterTime());
            waterResponses.add(userAccountWaterResponse);
        }
        return waterResponses;
    }
}
