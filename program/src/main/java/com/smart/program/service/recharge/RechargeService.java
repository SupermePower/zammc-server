package com.smart.program.service.recharge;

import com.smart.program.request.recharge.RechargeRequest;
import com.smart.program.response.recharge.RechargePackageResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface RechargeService {

    /**
     * 获取充值套餐
     *
     * @return
     * @throws Exception
     */
    List<RechargePackageResponse> queryRechargePackage() throws Exception;

    /**
     * 充值
     *
     * @param request
     * @throws Exception
     */
    Map<String, Object> recharge(RechargeRequest request) throws Exception;

    /**
     * 支付回调
     *
     * @param request
     * @return
     * @throws Exception
     */
    String getCallBack(HttpServletRequest request) throws Exception;
}
