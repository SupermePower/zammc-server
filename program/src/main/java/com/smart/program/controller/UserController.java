package com.smart.program.controller;

import com.smart.program.common.ErrorConstant;
import com.smart.program.request.user.AddUserRequest;
import com.smart.program.response.ResponseVO;
import com.smart.program.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 添加用户信息
     *
     * @return
     */
    @RequestMapping(path = "/addUser", method = RequestMethod.POST)
    public ResponseVO addUser(@RequestBody @Valid AddUserRequest request) {
        ResponseVO responseVO = new ResponseVO();
        try {
            userService.addUser(request);
            responseVO.setResult(ErrorConstant.SUCCESS_CODE, ErrorConstant.SUCCESS_MSG);
        } catch (Exception e) {
            log.error("UserController addUser request -> {} Exception \n", request.toString(), e);
            responseVO.setResult(ErrorConstant.ERROR_CODE, ErrorConstant.ERROR_MSG);
        }
        return responseVO;
    }
}
