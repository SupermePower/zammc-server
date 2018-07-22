package com.smart.program.repository.order;

import com.smart.program.domain.order.OrderItemEntity;
import com.smart.program.request.order.QueryOrderDetailRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemDao extends JpaRepository<OrderItemEntity, Long> {

    /**
     * 获取订单详情
     *
     * @return
     * @throws Exception
     */
    @Query(value = "select orderItem from OrderItemEntity orderItem where orderItem.orderId = :orderId and orderItem.dataStatus = '0'")
    List<OrderItemEntity> queryOrderItem(@Param("orderId") Long orderId) throws Exception;

    /**
     * 获取订单详情
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Query(value = "select orderDetail from OrderItemEntity orderDetail where orderDetail.orderId = :#{#request.orderId} and orderDetail.userId = :#{#request.userId}")
    List<OrderItemEntity> queryOrderDetail(@Param("request") QueryOrderDetailRequest request) throws Exception;
}
