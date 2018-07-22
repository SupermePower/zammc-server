package com.smart.program.controller;

import com.smart.program.common.ErrorConstant;
import com.smart.program.exception.BusinessException;
import com.smart.program.request.UserRequest;
import com.smart.program.request.coupon.ReceiveCouponRequest;
import com.smart.program.response.ResponseVO;
import com.smart.program.response.coupon.CouponResponse;
import com.smart.program.service.coupon.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/coupon")
@Slf4j
public class CouponController {

    @Autowired
    private CouponService couponService;

    /**
     * 获取代金券列表
     *
     * @param request
     * @return
     */
    @RequestMapping(path = "/queryCouponList", method = RequestMethod.POST)
    public ResponseVO<List<CouponResponse>> queryCouponList(@RequestBody @Valid UserRequest request) {
        ResponseVO<List<CouponResponse>> responseVO = new ResponseVO<>();
        try {
            List<CouponResponse> couponResponses = couponService.queryCouponList(request);
            responseVO.setResult(ErrorConstant.SUCCESS_CODE, ErrorConstant.SUCCESS_MSG, couponResponses);
        } catch (Exception e) {
            log.error("CouponController queryCouponList Exception \n", e);
            responseVO.setResult(ErrorConstant.ERROR_CODE, ErrorConstant.ERROR_MSG);
        }
        return responseVO;
    }

    /**
     * 领取代金券
     *
     * @param request
     * @return
     */
    @RequestMapping(path = "/receiveCoupon", method = RequestMethod.POST)
    public ResponseVO receiveCoupon(@RequestBody @Valid ReceiveCouponRequest request) {
        ResponseVO responseVO = new ResponseVO();
        try {
            couponService.receiveCoupon(request);
            responseVO.setResult(ErrorConstant.SUCCESS_CODE, ErrorConstant.SUCCESS_MSG);
        } catch (BusinessException b) {
            log.error("CouponController receiveCoupon request -> {} BusinessException \n", request.toString() + "userId: " + request.getUserId(), b);
            responseVO.setResult(b.getDealCode(), b.getMessage());
        } catch (Exception e) {
            log.error("CouponController receiveCoupon request -> {} Exception \n", request.toString() + "userId: " + request.getUserId(), e);
            responseVO.setResult(ErrorConstant.ERROR_CODE, ErrorConstant.ERROR_MSG);
        }
        return responseVO;
    }

    /**
     * 获取当前用户的代金券信息
     *
     * @param request
     * @return
     */
    @RequestMapping(path = "/queryUserCoupons")
    public ResponseVO<List<CouponResponse>> queryUserCoupons(@RequestBody @Valid UserRequest request) {
        ResponseVO<List<CouponResponse>> responseVO = new ResponseVO<>();
        try {
            List<CouponResponse> couponResponses = couponService.queryUserCoupons(request);
            responseVO.setResult(ErrorConstant.SUCCESS_CODE, ErrorConstant.SUCCESS_MSG, couponResponses);
        } catch (Exception e) {
            log.error("CouponController queryUserCoupons Exception \n", e);
            responseVO.setResult(ErrorConstant.ERROR_CODE, ErrorConstant.ERROR_MSG);
        }
        return responseVO;
    }
}
