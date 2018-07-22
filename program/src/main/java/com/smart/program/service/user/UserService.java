package com.smart.program.service.user;

import com.smart.program.request.user.AddUserRequest;

public interface UserService {

    /**
     * 添加用户信息
     *
     * @param request
     * @throws Exception
     */
    void addUser(AddUserRequest request) throws Exception;
}
