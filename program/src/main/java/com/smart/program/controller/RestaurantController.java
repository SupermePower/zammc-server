package com.smart.program.controller;

import com.smart.program.common.ErrorConstant;
import com.smart.program.domain.restaurant.RestaurantEntity;
import com.smart.program.response.ResponseVO;
import com.smart.program.response.restaurant.RestaurantPropertyResponse;
import com.smart.program.service.restaurant.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    /**
     * 获取酒店介绍信息
     *
     * @return
     */
    @RequestMapping(path = "/queryRestaurantIntroduce")
    public ResponseVO queryRestaurantIntroduce() {
        ResponseVO responseVO = new ResponseVO();
        try {

        } catch (Exception e) {
            log.error("RestaurantController queryRestaurantIntroduce Exception \n", e);
            responseVO.setResult(ErrorConstant.ERROR_CODE, ErrorConstant.ERROR_MSG);
        }
        return responseVO;
    }


    /**
     * 获取酒店介绍信息
     *
     * @return
     */
    @RequestMapping(path = "/queryRestaurantProperty")
    public ResponseVO queryRestaurantProperty() {
        ResponseVO responseVO = new ResponseVO();
        try {
            RestaurantPropertyResponse restaurantPropertyResponse = restaurantService.queryRestaurantProperty();
            responseVO.setResult(ErrorConstant.SUCCESS_CODE, ErrorConstant.SUCCESS_MSG, restaurantPropertyResponse);
        } catch (Exception e) {
            log.error("RestaurantController queryRestaurantIntroduce Exception \n", e);
            responseVO.setResult(ErrorConstant.ERROR_CODE, ErrorConstant.ERROR_MSG);
        }
        return responseVO;
    }

    /**
     * 获取店家信息
     *
     * @return
     */
    @RequestMapping(value = "/queryRestaurant")
    public ResponseVO<RestaurantEntity> queryRestaurant() {
        ResponseVO<RestaurantEntity> responseVO = new ResponseVO<>();
        try {
            RestaurantEntity restaurantEntity = restaurantService.queryRestaurant();
            responseVO.setResult(ErrorConstant.SUCCESS_CODE, ErrorConstant.SUCCESS_MSG, restaurantEntity);
        } catch (Exception e) {
            log.error("RestaurantController queryRestaurant Exception \n", e);
            responseVO.setResult(ErrorConstant.ERROR_CODE, ErrorConstant.ERROR_MSG);
        }
        return responseVO;
    }
}
