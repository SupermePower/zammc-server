package com.smart.program.request.account;

import com.smart.program.request.UserRequest;
import com.smart.program.response.account.UserAccountResponse;
import com.smart.program.response.account.UserAccountWaterResponse;

import java.util.List;

public interface UserAccountService {

    /**
     * 修改用户现金账户信息
     *
     * @param orderId
     * @throws Exception
     */
    void updateUserAccount(Long orderId) throws Exception;

    /**
     * 获取用户现金账户余额
     *
     * @param request
     * @return
     * @throws Exception
     */
    UserAccountResponse queryUserAccount(UserRequest request) throws Exception;

    /**
     * 查询用户现金账户流水信息
     *
     * @return
     * @throws Exception
     */
    List<UserAccountWaterResponse> queryUserAccountWater(UserRequest request) throws Exception;

}
