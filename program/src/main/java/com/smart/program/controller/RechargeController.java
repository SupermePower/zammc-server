package com.smart.program.controller;

import com.smart.program.common.ErrorConstant;
import com.smart.program.exception.BusinessException;
import com.smart.program.request.recharge.RechargeRequest;
import com.smart.program.response.ResponseVO;
import com.smart.program.response.recharge.RechargePackageResponse;
import com.smart.program.service.recharge.RechargeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recharge")
@Slf4j
public class RechargeController {

    @Autowired
    private RechargeService rechargeService;

    /**
     * 获取充值套餐
     *
     * @return
     */
    @RequestMapping(path = "/queryRechargePackage", method = RequestMethod.POST)
    public ResponseVO<List<RechargePackageResponse>> queryRechargePackage() {
        ResponseVO<List<RechargePackageResponse>> responseVO = new ResponseVO<>();
        try {
            List<RechargePackageResponse> rechargePackageResponses = rechargeService.queryRechargePackage();
            responseVO.setResult(ErrorConstant.SUCCESS_CODE, ErrorConstant.SUCCESS_MSG, rechargePackageResponses);
        } catch (Exception e) {
            log.error("RechargeController queryRechargePackage Exception", e);
            responseVO.setResult(ErrorConstant.ERROR_CODE, ErrorConstant.ERROR_MSG);
        }
        return responseVO;
    }

    /**
     * 获得签名等信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    public ResponseVO<Map<String, Object>> recharge(@RequestBody @Valid RechargeRequest request) {
        ResponseVO<Map<String, Object>> responseVO = new ResponseVO<>();
        try {
            Map<String, Object> recharge = rechargeService.recharge(request);
            responseVO.setResult(ErrorConstant.SUCCESS_CODE, ErrorConstant.SUCCESS_MSG, recharge);
        } catch (BusinessException b) {
            log.error("RechargeController recharge request -> {} BusinessException \n", request.toString(), b);
            responseVO.setResult(b.getDealCode(), b.getMessage());
        } catch (Exception e) {
            log.error("RechargeController recharge request -> {} Exception -> \n", request.toString(), e);
            responseVO.setResult(ErrorConstant.ERROR_CODE, ErrorConstant.ERROR_MSG);
        }
        return responseVO;
    }

    /**
     * 微信支付回调接口
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @SuppressWarnings({"rawtypes"})
    @RequestMapping("/callBack")
    public void callBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String resXml = rechargeService.getCallBack(request);
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error("RechargeController callBack Exception", e);
        }
    }
}
