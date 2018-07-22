package com.smart.program.controller;

import com.smart.program.common.ErrorConstant;
import com.smart.program.domain.restaurant.RestaurantEntity;
import com.smart.program.response.ResponseVO;
import com.smart.program.response.activity.ActivityResponse;
import com.smart.program.response.pay.PayResponse;
import com.smart.program.service.activity.ActivityService;
import com.smart.program.service.restaurant.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description 活动信息
 * @author: fly
 * @Date: 2018/7/22 下午6:50
 */
@RestController
@RequestMapping("/activity")
@Slf4j
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private RestaurantService restaurantService;

    /**
     * 查询活动信息
     *
     * @return
     */
    @RequestMapping(path = "/queryActivity", method = RequestMethod.POST)
    public ResponseVO<PayResponse> queryActivity() {
        ResponseVO<PayResponse> responseVO = new ResponseVO<>();
        try {
            List<ActivityResponse> activityResponses = activityService.queryActivity();
            RestaurantEntity restaurantEntity = restaurantService.queryRestaurant();
            PayResponse payResponse = new PayResponse();
            payResponse.setActivityResponses(activityResponses);
            payResponse.setRestaurant(restaurantEntity);
            responseVO.setResult(ErrorConstant.SUCCESS_CODE, ErrorConstant.SUCCESS_MSG, payResponse);
        } catch (Exception e) {
            log.error("ActivityController queryActivity Exception", e);
            responseVO.setResult(ErrorConstant.ERROR_CODE, ErrorConstant.ERROR_MSG);
        }
        return responseVO;
    }
}
