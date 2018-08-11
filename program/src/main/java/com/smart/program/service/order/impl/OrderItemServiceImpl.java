package com.smart.program.service.order.impl;

import com.smart.program.domain.order.OrderInfoEntity;
import com.smart.program.domain.order.OrderItemEntity;
import com.smart.program.domain.restaurant.RestaurantEntity;
import com.smart.program.repository.order.OrderItemDao;
import com.smart.program.request.order.QueryOrderDetailRequest;
import com.smart.program.response.order.OrderDetailResponse;
import com.smart.program.response.order.OrderDetailResponseList;
import com.smart.program.service.order.OrderItemService;
import com.smart.program.service.order.OrderService;
import com.smart.program.service.restaurant.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestaurantService restaurantService;

    /**
     * 订单详情
     *
     * @param orderId
     * @return
     * @throws Exception
     */
    @Override
    public List<OrderItemEntity> queryOrderItem(Long orderId) throws Exception {
        return orderItemDao.queryOrderItem(orderId);
    }

    /**
     * 获取订单详情
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public OrderDetailResponseList queryOrderDetail(QueryOrderDetailRequest request) throws Exception {
        OrderDetailResponseList orderDetailResponseList = new OrderDetailResponseList();
        // 获取店铺信息
        RestaurantEntity restaurantEntity = restaurantService.queryRestaurant();
        orderDetailResponseList.setRestaurantId(restaurantEntity.getRestaurantId());
        orderDetailResponseList.setRestaurantName(restaurantEntity.getRestaurantName());
        orderDetailResponseList.setRestaurantImg(restaurantEntity.getRestaurantImg());
        // 获取订单信息
        OrderInfoEntity orderInfoEntity = orderService.queryOrderInfoById(request.getOrderId());
        orderDetailResponseList.setOrderTime(orderInfoEntity.getCreateTime());
        orderDetailResponseList.setOrderPrice(orderInfoEntity.getTotalprice());
        //获取订单详细信息
        List<OrderItemEntity> orderItemEntities = orderItemDao.queryOrderDetail(request);
        List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        for (OrderItemEntity orderItemEntity : orderItemEntities) {
            OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
            orderDetailResponse.setGoodsId(orderItemEntity.getGoodsId());
            orderDetailResponse.setGoodsName(orderItemEntity.getGoodsName());
            orderDetailResponse.setGoodsPrice(orderItemEntity.getGoodsPrice());
            orderDetailResponses.add(orderDetailResponse);
        }
        orderDetailResponseList.setOrderDetailResponses(orderDetailResponses);
        return orderDetailResponseList;
    }
}
