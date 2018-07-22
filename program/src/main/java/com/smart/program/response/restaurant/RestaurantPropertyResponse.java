package com.smart.program.response.restaurant;

import com.smart.program.domain.restaurant.RestaurentPropertyDTO;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantPropertyResponse {
    private List<RestaurentPropertyDTO> navbar;
}
