package com.smart.program.domain.restaurant;

import lombok.Data;

/**
 * com.smart.program.domain.restaurant
 * Created by baihe
 * ON 2018/5/23.
 */
@Data
public class RestaurentPropertyDTO {
    private String machineName;
    private String image;
    private String msg;
    private int displaySequence;


    public RestaurentPropertyDTO(RestaurantPropertyEntity entity){
        this.displaySequence=entity.getSort();
        this.image=entity.getPropertyImg();
        this.msg=entity.getPropertyMsg();
        this.machineName=entity.getRestaurantProperty();
    }
}
