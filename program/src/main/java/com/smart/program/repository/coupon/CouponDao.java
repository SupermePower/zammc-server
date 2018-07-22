package com.smart.program.repository.coupon;

import com.smart.program.domain.coupon.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponDao extends JpaRepository<CouponEntity, Long> {


    /**
     * 获取代金券列表
     *
     * @return
     * @throws Exception
     */
    @Query(value = "select coupon from CouponEntity coupon where coupon.dataStatus = '0'")
    List<CouponEntity> queryCouponList() throws Exception;

    /**
     * 获取代金券信息根据代金券主键
     *
     * @param couponId
     * @return
     * @throws Exception
     */
    @Query(value = "select coupon from CouponEntity coupon where coupon.id = :couponId and coupon.dataStatus = '0'")
    CouponEntity queryCouponById(@Param("couponId") Long couponId) throws Exception;
}
