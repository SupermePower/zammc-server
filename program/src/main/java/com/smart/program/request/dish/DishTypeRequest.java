package com.smart.program.request.dish;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DishTypeRequest {
    @NotNull(message = "菜品id不能为空")
    private Long goodsId;
}
