package com.smart.program.repository.recharge;

import com.smart.program.domain.recharge.RechargePackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RechargePackageRepository extends JpaRepository<RechargePackageEntity, Long> {

    /**
     * 获取充值套餐列表
     *
     * @return
     * @throws Exception
     */
    @Query(value = "select package from RechargePackageEntity package where package.dataStatus = '0'")
    List<RechargePackageEntity> queryRechargePackageList() throws Exception;

    /**
     * 获取套餐信息
     *
     * @param packageId
     * @return
     * @throws Exception
     */
    @Query(value = "select package from RechargePackageEntity package where package.packageId = :packageId and package.dataStatus = '0'")
    RechargePackageEntity queryPackageById(@Param("packageId") Long packageId) throws Exception;
}
