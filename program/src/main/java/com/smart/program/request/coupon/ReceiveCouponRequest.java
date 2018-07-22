package com.smart.program.request.coupon;

import com.smart.program.request.UserRequest;

public class ReceiveCouponRequest extends UserRequest {

    private Long couponId;

    public ReceiveCouponRequest() {
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    @Override
    public String toString() {
        return "ReceiveCouponRequest{" +
                "couponId=" + couponId +
                '}';
    }
}
