package com.smart.program.response.order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * 订单详情响应对象
 */
public class OrderDetailResponseList {

    /**店铺主键*/
    private Long restaurantId;
    /**店铺名称*/
    private String restaurantName;
    /**店铺图片*/
    private String restaurantImg;
    /**下单时间*/
    private Timestamp orderTime;
    /**下单订单价格*/
    private BigDecimal orderPrice;
    /**商品信息*/
    private List<OrderDetailResponse> orderDetailResponses;

    public OrderDetailResponseList() {
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantImg() {
        return restaurantImg;
    }

    public void setRestaurantImg(String restaurantImg) {
        this.restaurantImg = restaurantImg;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public List<OrderDetailResponse> getOrderDetailResponses() {
        return orderDetailResponses;
    }

    public void setOrderDetailResponses(List<OrderDetailResponse> orderDetailResponses) {
        this.orderDetailResponses = orderDetailResponses;
    }
}
