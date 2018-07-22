package com.smart.program.service.coupon;

import com.smart.program.request.UserRequest;
import com.smart.program.request.coupon.ReceiveCouponRequest;
import com.smart.program.response.coupon.CouponResponse;

import java.util.List;

public interface CouponService {

    /**
     * 获取代金券列表
     *
     * @param request
     * @return
     * @throws Exception
     */
    List<CouponResponse> queryCouponList(UserRequest request) throws Exception;

    /**
     * 领取代金券
     *
     * @param request
     * @throws Exception
     */
    void receiveCoupon(ReceiveCouponRequest request) throws Exception;

    /**
     * 获取用户代金券信息
     *
     * @return
     * @throws Exception
     */
    List<CouponResponse> queryUserCoupons(UserRequest request) throws Exception;
}
