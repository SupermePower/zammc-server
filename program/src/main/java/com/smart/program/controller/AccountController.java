package com.smart.program.controller;

import com.smart.program.common.ErrorConstant;
import com.smart.program.request.UserRequest;
import com.smart.program.request.account.UserAccountService;
import com.smart.program.response.ResponseVO;
import com.smart.program.response.account.UserAccountResponse;
import com.smart.program.response.account.UserAccountWaterResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController {

    @Autowired
    private UserAccountService userAccountService;

    /**
     * 查询用户现金账户
     *
     * @param request
     * @return
     */
    @RequestMapping(path = "/queryUserAccount", method = RequestMethod.POST)
    public ResponseVO<UserAccountResponse> queryUserAccount(@RequestBody @Valid UserRequest request) {
        ResponseVO<UserAccountResponse> responseVO = new ResponseVO<>();
        try {
            UserAccountResponse userAccountResponse = userAccountService.queryUserAccount(request);
            responseVO.setResult(ErrorConstant.SUCCESS_CODE, ErrorConstant.SUCCESS_MSG, userAccountResponse);
        } catch (Exception e) {
            log.error("AccountController queryUserAccount request -> {} Exception \n", request.toString(), e);
            responseVO.setResult(ErrorConstant.ERROR_CODE, ErrorConstant.ERROR_MSG);
        }
        return responseVO;
    }

    /**
     * 查询用户现金账户流失信息
     *
     * @param request
     * @return
     */
    @RequestMapping(path = "/queryUserAccountWater", method = RequestMethod.POST)
    public ResponseVO<List<UserAccountWaterResponse>> queryUserAccountWater(UserRequest request) {
        ResponseVO<List<UserAccountWaterResponse>> responseVO = new ResponseVO<>();
        try {
            List<UserAccountWaterResponse> waterResponses = userAccountService.queryUserAccountWater(request);
            responseVO.setResult(ErrorConstant.SUCCESS_CODE, ErrorConstant.SUCCESS_MSG, waterResponses);
        } catch (Exception e) {
            log.error("AccountController queryUserAccount request -> {} Exception \n", request.toString(), e);
            responseVO.setResult(ErrorConstant.ERROR_CODE, ErrorConstant.ERROR_MSG);
        }
        return responseVO;
    }
}
