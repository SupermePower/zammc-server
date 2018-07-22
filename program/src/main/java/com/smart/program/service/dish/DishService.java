package com.smart.program.service.dish;

import com.smart.program.domain.goods.GoodsDTO;
import com.smart.program.request.dish.DishTypeRequest;
import com.smart.program.response.dish.DishListResponse;
import com.smart.program.response.dish.DishTypeResponse;

import java.util.List;

public interface DishService {

    DishListResponse findDishList() throws Exception;

    DishTypeResponse goodsType(DishTypeRequest request) throws Exception;
}
