package com.smart.program.response.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @description 下单响应参数
 * @author: liying.fu
 * @Date: 2018/9/5 下午9:52
 */
@Getter
@Setter
@ToString
public class PlaceOrderResponse {
    private BigDecimal totalPrice;
    private Long orderId;
}
