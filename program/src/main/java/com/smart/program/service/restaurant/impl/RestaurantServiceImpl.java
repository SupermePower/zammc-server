package com.smart.program.service.restaurant.impl;

import com.smart.program.domain.restaurant.RestaurantEntity;
import com.smart.program.domain.restaurant.RestaurantPropertyEntity;
import com.smart.program.domain.restaurant.RestaurentPropertyDTO;
import com.smart.program.repository.restaurant.RestaurantPropertyRepository;
import com.smart.program.repository.restaurant.RestaurantRepository;
import com.smart.program.response.restaurant.RestaurantPropertyResponse;
import com.smart.program.service.restaurant.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantPropertyRepository propertyRepository;
    /**
     * 获取商店基本信息
     *
     * @return
     * @throws Exception
     */
    @Override
    public RestaurantEntity queryRestaurant() throws Exception {
        return restaurantRepository.queryRestaurantEntity();
    }


    /**
     * 查询店铺详情
     * @return
     * @throws Exception
     */
    @Override
    public RestaurantPropertyResponse queryRestaurantProperty() throws Exception {
        List<RestaurantPropertyEntity> all = propertyRepository.findAll();
        List<RestaurentPropertyDTO> collect = all.stream().map(RestaurentPropertyDTO::new).collect(Collectors.toList());
        RestaurantPropertyResponse restaurantPropertyResponse = new RestaurantPropertyResponse();
        restaurantPropertyResponse.setNavbar(collect);
        return restaurantPropertyResponse;
    }
}
