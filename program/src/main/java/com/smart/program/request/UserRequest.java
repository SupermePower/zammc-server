package com.smart.program.request;

import javax.validation.constraints.NotNull;

/**
 * 用户请求参数对象
 */
public class UserRequest {

    @NotNull(message = "请登录")
    private String userId;

    public UserRequest() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "userId=" + userId +
                '}';
    }
}
