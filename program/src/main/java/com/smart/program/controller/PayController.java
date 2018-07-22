package com.smart.program.controller;

import com.smart.program.common.ErrorConstant;
import com.smart.program.exception.BusinessException;
import com.smart.program.request.pay.PayRequest;
import com.smart.program.response.ResponseVO;
import com.smart.program.service.pay.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/pay")
@Slf4j
public class PayController {


    @Autowired
    private PayService payService;

    /**
     * 转账
     *
     * @param request
     * @return
     */
    @RequestMapping(path = "/pay", method = RequestMethod.POST)
    public ResponseVO pay(@RequestBody @Valid PayRequest request) {
        ResponseVO responseVO = new ResponseVO();
        try {
            payService.pay(request);
        } catch (BusinessException b) {
            log.error("PayController pay request -> {} BusinessException \n", request.toString(), b);
            responseVO.setResult(b.getDealCode(), b.getMessage());
        } catch (Exception e) {
            log.error("PayController pay request -> {} Exception \n", request.toString(), e);
            responseVO.setResult(ErrorConstant.ERROR_CODE, ErrorConstant.ERROR_MSG);
        }
        return responseVO;
    }


}
