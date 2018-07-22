package com.smart.program.repository.account;

import com.smart.program.domain.account.AccountWaterEntity;
import com.smart.program.request.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountWaterDao extends JpaRepository<AccountWaterEntity, Long> {

    /**
     * 获取用户现金账户流水信息
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Query(value = "select water from AccountWaterEntity water where water.userId = :#{#request.userId} and water.dataStatus = '0'")
    List<AccountWaterEntity> queryUserAccountWaterByUserId(@Param("request") UserRequest request) throws Exception;
}
