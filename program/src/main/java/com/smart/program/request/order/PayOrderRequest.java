package com.smart.program.request.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description 订单支付请求对象
 * @author: liying.fu
 * @Date: 2018/9/6 下午11:09
 */
@Setter
@Getter
@ToString
public class PayOrderRequest {
    private Long orderId;
    private Byte payWay;
    private String memo;
}