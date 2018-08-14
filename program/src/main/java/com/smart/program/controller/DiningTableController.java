package com.smart.program.controller;

import com.smart.program.common.ErrorConstant;
import com.smart.program.response.ResponseVO;
import com.smart.program.service.dining.DiningTableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description 餐位信息控制器
 * @author: liying.fu
 * @Date: 2018/8/14 下午10:12
 */
@RestController
@RequestMapping("/dining")
@Slf4j
public class DiningTableController {

    @Autowired
    private DiningTableService diningTableService;

    /**
     * 修改餐位状态
     *
     * @param id 餐位主键
     * @return
     */
    @GetMapping(path = "/updateDiningTableStatus/{id}")
    public ResponseVO updateDiningTableStatus(@PathVariable Long id) {
        ResponseVO responseVO = new ResponseVO();
        try {
            diningTableService.updateStatus(id);
            responseVO.setResult(ErrorConstant.SUCCESS_CODE, ErrorConstant.SUCCESS_MSG);
        } catch (Exception e) {
            log.error("DiningTableController updateDiningTableStatus tableId -> {} Exception", id, e);
            responseVO.setResult(ErrorConstant.ERROR_CODE, ErrorConstant.ERROR_MSG);
        }
        return responseVO;
    }
}
