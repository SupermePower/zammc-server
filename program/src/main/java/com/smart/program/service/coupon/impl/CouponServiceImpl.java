package com.smart.program.service.coupon.impl;

import com.smart.program.common.ErrorConstant;
import com.smart.program.domain.coupon.CouponEntity;
import com.smart.program.domain.coupon.CouponUserEntity;
import com.smart.program.domain.restaurant.RestaurantEntity;
import com.smart.program.exception.BusinessException;
import com.smart.program.idwork.IdWorker;
import com.smart.program.repository.coupon.CouponDao;
import com.smart.program.repository.coupon.CouponUserDao;
import com.smart.program.repository.restaurant.RestaurantRepository;
import com.smart.program.request.UserRequest;
import com.smart.program.request.coupon.ReceiveCouponRequest;
import com.smart.program.response.coupon.CouponResponse;
import com.smart.program.service.coupon.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private CouponUserDao couponUserDao;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private IdWorker idWorker;

    /**
     * 获取代金券列表
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public List<CouponResponse> queryCouponList(UserRequest request) throws Exception {
        List<CouponResponse> couponResponses = new ArrayList<>();
        RestaurantEntity restaurantEntity = restaurantRepository.queryRestaurantEntity();
        List<CouponEntity> couponEntities = couponDao.queryCouponList();
        for (CouponEntity couponEntity : couponEntities) {
            CouponResponse couponResponse = getCouponResponse(request, restaurantEntity, couponEntity);
            couponResponses.add(couponResponse);
        }
        return couponResponses;
    }

    /**
     * 获取优惠券列表信息
     *
     * @param request
     * @param restaurantEntity
     * @param couponEntity
     * @return
     * @throws Exception
     */
    private CouponResponse getCouponResponse(UserRequest request, RestaurantEntity restaurantEntity, CouponEntity couponEntity) throws Exception {
        CouponResponse couponResponse = new CouponResponse();
        couponResponse.setRestaurantName(restaurantEntity.getRestaurantName());
        couponResponse.setCouponId(couponEntity.getId());
        couponResponse.setCouponCondition(couponEntity.getCouponCondition());
        LocalDate localDate = couponEntity.getCouponTime().toLocalDateTime().toLocalDate();
        couponResponse.setCouponTime(localDate);
        couponResponse.setCouponType(couponEntity.getCouponType());
        couponResponse.setCouponName(couponEntity.getCouponName());
        Timestamp couponTime = couponEntity.getCouponTime();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (timestamp.before(couponTime)) {//未过期
            couponResponse.setExpire(0);
        } else {//过期
            couponResponse.setExpire(1);
        }
        CouponUserEntity couponUserEntity = couponUserDao.queryUserCoupon(couponEntity.getId(), request.getUserId());
        couponResponse.setIsReceive(couponUserEntity == null ? (byte) 0 : (byte) 1);
        return couponResponse;
    }

    /**
     * 领取代金券
     *
     * @param request
     * @throws Exception
     */
    @Override
    @Transactional
    public void receiveCoupon(ReceiveCouponRequest request) throws Exception {
        CouponUserEntity couponUserEntity = couponUserDao.queryUserCoupon(request.getCouponId(), request.getUserId());
        if (couponUserEntity != null) {
            throw new BusinessException(ErrorConstant.COUPON_IS_RECEIVED_ERROR, ErrorConstant.COUPON_IS_RECEIVED_ERROR_MSG);
        } else {
            CouponEntity couponEntity = couponDao.queryCouponById(request.getCouponId());
            if (couponEntity.getCouponNum() <= 0) {
                throw new BusinessException(ErrorConstant.COUPON_IS_NOT_ENOUGH_ERROR, ErrorConstant.COUPON_IS_NOT_ENOUGH_ERROR_MSG);
            }
            couponUserEntity = new CouponUserEntity();
            couponUserEntity.setId(idWorker.nextId());
            couponUserEntity.setCouponId(request.getCouponId());
            couponUserEntity.setUserId(request.getUserId());
            couponUserDao.saveAndFlush(couponUserEntity);

            couponEntity.setCouponNum(couponEntity.getCouponNum() - 1);
            couponDao.saveAndFlush(couponEntity);
        }
    }

    /**
     * 获取用户代金券信息
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<CouponResponse> queryUserCoupons(UserRequest request) throws Exception {
        List<CouponResponse> couponResponses = new ArrayList<>();
        RestaurantEntity restaurantEntity = restaurantRepository.queryRestaurantEntity();
        List<CouponUserEntity> couponUserEntities = couponUserDao.queryCouponByUser(request);
        for (CouponUserEntity couponEntities : couponUserEntities) {
            CouponEntity couponEntity = couponDao.queryCouponById(couponEntities.getCouponId());
            CouponResponse couponResponse = getCouponResponse(request, restaurantEntity, couponEntity);
            couponResponses.add(couponResponse);
        }
        return couponResponses;
    }
}
