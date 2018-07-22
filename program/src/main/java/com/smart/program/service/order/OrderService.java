package com.smart.program.service.order;

import com.smart.program.domain.order.OrderInfoEntity;
import com.smart.program.request.UserRequest;
import com.smart.program.request.order.PlaceOrderRequest;
import com.smart.program.response.order.OrderResponseList;

import java.math.BigDecimal;

public interface OrderService {

    /**
     * 获取用户订单信息
     *
     * @param request
     * @return
     * @throws Exception
     */
    OrderResponseList queryUserOrder(UserRequest request) throws Exception;

    BigDecimal placeOrder(PlaceOrderRequest request);

    /**
     * 根据订单主键获取订单信息
     *
     * @param orderId
     * @return
     * @throws Exception
     */
    OrderInfoEntity queryOrderInfoById(long orderId) throws Exception;
}
