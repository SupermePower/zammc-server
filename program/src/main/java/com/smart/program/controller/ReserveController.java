package com.smart.program.controller;

import com.smart.program.common.ErrorConstant;
import com.smart.program.request.reserve.UserReserveRequest;
import com.smart.program.response.ResponseVO;
import com.smart.program.service.reserve.ReserveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/reserve")
@Slf4j
public class ReserveController {

    @Autowired
    private ReserveService reserveService;

    /**
     * 用户预定
     *
     * @param request
     * @return
     */
    @RequestMapping(path = "/userReserve", method = RequestMethod.POST)
    public ResponseVO userReserve(@RequestBody @Valid UserReserveRequest request) {
        ResponseVO responseVO = new ResponseVO();
        try {
            reserveService.userReserve(request);
            responseVO.setResult(ErrorConstant.SUCCESS_CODE, ErrorConstant.SUCCESS_MSG);
        } catch (Exception e) {
            log.error("ReserveController userReserve request -> {} Exception \n", request.toString(), e);
            responseVO.setResult(ErrorConstant.ERROR_CODE, ErrorConstant.ERROR_MSG);
        }
        return responseVO;
    }
}
