package com.smart.program.controller;

import com.smart.program.common.ErrorConstant;
import com.smart.program.response.ResponseVO;
import com.smart.program.response.login.LoginResponse;
import com.smart.program.service.login.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description 小程序登录
 * @author: liying.fu
 * @Date: 2018/9/12 上午1:50
 */
@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录
     *
     * @param jsCode
     * @return
     */
    @GetMapping("/login/{jsCode}")
    public ResponseVO<LoginResponse> login(@PathVariable String jsCode) {
        ResponseVO<LoginResponse> responseVO = new ResponseVO<>();
        try {
            LoginResponse loginResponse = loginService.login(jsCode);
            responseVO.setResult(ErrorConstant.SUCCESS_CODE, ErrorConstant.SUCCESS_MSG, loginResponse);
        } catch (Exception e) {
            log.error("LoginController login jsCode -> {} Exception", jsCode, e);
            responseVO.setResult(ErrorConstant.ERROR_CODE, ErrorConstant.ERROR_MSG);
        }
        return responseVO;
    }
}
