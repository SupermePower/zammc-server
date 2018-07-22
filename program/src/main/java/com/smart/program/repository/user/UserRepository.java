package com.smart.program.repository.user;

import com.smart.program.domain.user.UserInfoEntity;
import com.smart.program.request.user.AddUserRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserInfoEntity, String> {

    @Query(value = "select user from UserInfoEntity user where user.userId = :#{#request.userId}")
    UserInfoEntity queryUserById(@Param("request") AddUserRequest request) throws Exception;
}
