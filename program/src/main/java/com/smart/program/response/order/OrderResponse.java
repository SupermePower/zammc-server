package com.smart.program.response.order;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderResponse {

    /**订单主键*/
    private Long orderId;
    /**商户主键*/
    private Long restaurantId;
    /**商户LOGO*/
    private String restaurantUrl;
    /**商户名称*/
    private String restaurantName;
    /**商户编号*/
    private String restaurantCode;
    /**创建时间*/
    private Timestamp createTime;
    /**订单状态*/
    private Byte orderStatus;
    /**消费金额*/
    private BigDecimal money;
    /**优惠金额*/
    private BigDecimal delMoney;
    /**实付金额*/
    private BigDecimal actMoney;

    public OrderResponse() {
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

    public String getRestaurantUrl() {
        return restaurantUrl;
    }

    public void setRestaurantUrl(String restaurantUrl) {
        this.restaurantUrl = restaurantUrl;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantCode() {
        return restaurantCode;
    }

    public void setRestaurantCode(String restaurantCode) {
        this.restaurantCode = restaurantCode;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getDelMoney() {
        return delMoney;
    }

    public void setDelMoney(BigDecimal delMoney) {
        this.delMoney = delMoney;
    }

    public BigDecimal getActMoney() {
        return actMoney;
    }

    public void setActMoney(BigDecimal actMoney) {
        this.actMoney = actMoney;
    }
}
