package com.smart.program.service.pay;

import com.smart.program.request.pay.PayRequest;

import java.util.Map;

public interface PayService {

    /**
     * 转账
     *
     * @param request
     * @throws Exception
     * @return
     */
    Map<String, Object> pay(PayRequest request) throws Exception;
}
