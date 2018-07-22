package com.smart.program.repository.coupon;

import com.smart.program.domain.coupon.CouponUserEntity;
import com.smart.program.request.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponUserDao extends JpaRepository<CouponUserEntity, Long> {

    @Query(value = "select couponUser from CouponUserEntity couponUser where " +
            "couponUser.couponId = :couponId and couponUser.userId = :userId " +
            "and couponUser.dataStatus = '0'")
    CouponUserEntity queryUserCoupon(@Param("couponId") Long couponId, @Param("userId") String userId) throws Exception;

    @Query(value = "select couponUser from CouponUserEntity couponUser where couponUser.userId = :#{#request.userId} and couponUser.dataStatus = '1'")
    List<CouponUserEntity> queryCouponByUser(@Param("request") UserRequest request) throws Exception;

}
