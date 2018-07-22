package com.smart.program.service.order;

import com.smart.program.domain.order.OrderItemEntity;
import com.smart.program.request.order.QueryOrderDetailRequest;
import com.smart.program.response.order.OrderDetailResponseList;

import java.util.List;

public interface OrderItemService {

    /**
     * 订单详情
     *
     * @param orderId
     * @return
     * @throws Exception
     */
    List<OrderItemEntity> queryOrderItem(Long orderId) throws Exception;

    /**
     * 获取订单详情
     *
     * @param request
     * @return
     * @throws Exception
     */
    OrderDetailResponseList queryOrderDetail(QueryOrderDetailRequest request) throws Exception;
}
