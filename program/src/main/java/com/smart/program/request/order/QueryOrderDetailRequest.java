package com.smart.program.request.order;


import javax.validation.constraints.NotNull;

/**
 * 获取订单详情请求对象
 */
public class QueryOrderDetailRequest {

    @NotNull(message = "用户不能为空")
    private Long userId;
    @NotNull(message = "订单主键不能为空")
    private Long orderId;
    @NotNull(message = "商户主键不能为空")
    private Long restaurantId;

    public QueryOrderDetailRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "QueryOrderDetailRequest{" +
                "userId=" + userId +
                ", orderId=" + orderId +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
