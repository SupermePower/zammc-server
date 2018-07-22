package com.smart.program.repository.account;


import com.smart.program.domain.account.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDao extends JpaRepository<AccountEntity, Long> {


    /**
     * 根据用户主键获取用户现金账户信息
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @Query(value = "select account from AccountEntity account where account.userId = :userId and account.dataStatus = '0'")
    AccountEntity queryUserAccountByUserId(@Param("userId") String userId) throws Exception;

}
