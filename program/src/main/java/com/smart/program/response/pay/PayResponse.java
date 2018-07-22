package com.smart.program.response.pay;

import com.smart.program.domain.restaurant.RestaurantEntity;
import com.smart.program.response.activity.ActivityResponse;

import java.util.List;

/**
 * @description 支付页面相关数据
 * @author: fly
 * @Date: 2018/7/22 下午7:49
 */
public class PayResponse {

    private RestaurantEntity restaurant;
    private List<ActivityResponse> activityResponses;

    public PayResponse() {
    }

    public RestaurantEntity getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantEntity restaurant) {
        this.restaurant = restaurant;
    }

    public List<ActivityResponse> getActivityResponses() {
        return activityResponses;
    }

    public void setActivityResponses(List<ActivityResponse> activityResponses) {
        this.activityResponses = activityResponses;
    }

    @Override
    public String toString() {
        return "PayResponse{" +
                "restaurant=" + restaurant +
                ", activityResponses=" + activityResponses +
                '}';
    }
}