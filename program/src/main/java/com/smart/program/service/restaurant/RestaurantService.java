package com.smart.program.service.restaurant;

import com.smart.program.domain.restaurant.RestaurantEntity;
import com.smart.program.response.restaurant.RestaurantPropertyResponse;

public interface RestaurantService {

    /**
     * 获取商店基本信息
     *
     * @return
     * @throws Exception
     */
    RestaurantEntity queryRestaurant() throws Exception;


    /**
     * 获取商店基本信息
     *
     * @return
     * @throws Exception
     */
    RestaurantPropertyResponse queryRestaurantProperty() throws Exception;
}
