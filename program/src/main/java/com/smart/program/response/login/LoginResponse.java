package com.smart.program.response.login;

import lombok.Getter;
import lombok.Setter;

/**
 * @description 登录响应对象
 * @author: liying.fu
 * @Date: 2018/9/12 上午2:04
 */
@Setter
@Getter
public class LoginResponse {

    private String openid;
    private String session_key;
    private String unionid;
}
