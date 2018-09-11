package com.smart.program.service.login;

import com.smart.program.response.login.LoginResponse;

/**
 * @description 用户登录业务
 * @author: liying.fu
 * @Date: 2018/9/12 上午2:06
 */
public interface LoginService {

    /**
     * 登录
     *
     * @param jsCode
     * @return
     * @throws Exception
     */
    LoginResponse login(String jsCode) throws Exception;
}
