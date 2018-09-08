package com.smart.program.request.order;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PlaceOrderRequest {
    @NotNull(message = "用户id不能为空")
    private String userId;
    @NotNull(message = "选择商品不能为空")
    private List<OrderItemDTO2> goodMsg;

    private Integer allCount;

    private BigDecimal allMoney;

    private String tableCode;
}
