package com.smart.program.response.dish;

import com.smart.program.domain.goods.GoodsDTO;
import com.smart.program.domain.goods.NoticeDTO;
import com.smart.program.domain.goods.RestaurantDTO;
import lombok.Data;

import java.util.List;

@Data
public class DishListResponse {
    private List<NoticeDTO> notice;
    private RestaurantDTO restaurant;
    private List<GoodsDTO> dishList;
}
