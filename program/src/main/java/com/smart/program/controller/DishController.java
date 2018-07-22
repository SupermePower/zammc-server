package com.smart.program.controller;

import com.smart.program.common.ErrorConstant;
import com.smart.program.request.dish.DishTypeRequest;
import com.smart.program.response.ResponseVO;
import com.smart.program.response.dish.DishListResponse;
import com.smart.program.response.dish.DishTypeResponse;
import com.smart.program.service.dish.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 获取菜单列表
     *
     * @return
     */
    @RequestMapping(path = "/dishList", method = RequestMethod.POST)
    public ResponseVO userReserve() {
        ResponseVO responseVO = new ResponseVO();
        try {
            DishListResponse dishList = dishService.findDishList();
            responseVO.setResult(ErrorConstant.SUCCESS_CODE, ErrorConstant.SUCCESS_MSG,dishList);
        } catch (Exception e) {
            log.error("ReserveController userReserve request -> {} Exception ",e);
            responseVO.setResult(ErrorConstant.ERROR_CODE, ErrorConstant.ERROR_MSG);
        }
        return responseVO;
    }

    /**
     * 获取菜品规格
     * @param request
     * @return
     */
    @RequestMapping(path = "/goodsType", method = RequestMethod.POST)
    public ResponseVO goodsType(@RequestBody @Valid DishTypeRequest request) {
        log.info("request ----->{}",request);
        ResponseVO responseVO = new ResponseVO();
        try {
            DishTypeResponse dishTypeResponse = dishService.goodsType(request);
            responseVO.setResult(ErrorConstant.SUCCESS_CODE, ErrorConstant.SUCCESS_MSG,dishTypeResponse);
        } catch (Exception e) {
            log.error("ReserveController userReserve request -> {} Exception ");
            responseVO.setResult(ErrorConstant.ERROR_CODE, ErrorConstant.ERROR_MSG);
        }
        return responseVO;
    }
}
