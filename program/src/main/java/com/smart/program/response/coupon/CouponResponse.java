package com.smart.program.response.coupon;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CouponResponse {

    private Long couponId;
    private Byte couponType;
    private LocalDate couponTime;
    private BigDecimal couponCondition;
    private String restaurantName;
    private Byte isReceive;
    private String couponName;
    private int expire;

    public CouponResponse() {
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Byte getCouponType() {
        return couponType;
    }

    public void setCouponType(Byte couponType) {
        this.couponType = couponType;
    }

    public LocalDate getCouponTime() {
        return couponTime;
    }

    public void setCouponTime(LocalDate couponTime) {
        this.couponTime = couponTime;
    }


    public BigDecimal getCouponCondition() {
        return couponCondition;
    }

    public void setCouponCondition(BigDecimal couponCondition) {
        this.couponCondition = couponCondition;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Byte getIsReceive() {
        return isReceive;
    }

    public void setIsReceive(Byte isReceive) {
        this.isReceive = isReceive;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }
}
