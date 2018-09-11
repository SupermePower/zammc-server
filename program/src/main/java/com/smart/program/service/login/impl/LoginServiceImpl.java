package com.smart.program.service.login.impl;

import com.alibaba.fastjson.JSONObject;
import com.smart.program.common.HttpHelper;
import com.smart.program.common.HttpResult;
import com.smart.program.response.login.LoginResponse;
import com.smart.program.service.login.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @description 登录业务实现
 * @author: liying.fu
 * @Date: 2018/9/12 上午2:07
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final String url = "https://api.weixin.qq.com/sns/jscode2session";

    private final String appid = "wxfc61b3daa2d95647";

    private final String secret = "b89e881163d43de3e7c5868dbb496d62";

    private final String grant_type = "authorization_code";

    /**
     * 用户登录
     *
     * @param jsCode
     * @return
     */
    @Override
    public LoginResponse login(String jsCode) throws Exception {
        String url = getUrl(this.url, appid, secret, grant_type, jsCode);
        HttpResult result = HttpHelper.getInstance().get(url, new HashMap<>());
        log.info("get openid result -> {} ->{}", result.getContent(), result.isOk());
        if (!result.isOk()) {
            return null;
        }
        JSONObject rtn = JSONObject.parseObject(result.getContent());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setOpenid(rtn.getString("openid"));
        return loginResponse;
    }

    /**
     * 获取请求URL
     * @param url
     * @param appid
     * @param secret
     * @param grant_type
     * @param jsCode
     * @return
     */
    private String getUrl(String url, String appid, String secret, String grant_type, String jsCode) {
        return url + "?appid=" + appid + "&secret=" + secret + "&js_code=" + jsCode + "&grant_type=" + grant_type;
    }
}
