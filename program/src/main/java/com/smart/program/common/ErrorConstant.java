package com.smart.program.common;

/**
 * 系统异常处理信息
 */
public class ErrorConstant {

    public static final int SUCCESS_CODE = 200;
    public static final String SUCCESS_MSG = "SUCCESS";

    public static final int ERROR_CODE = 500;
    public static final String ERROR_MSG = "系统异常";

    //转账相关
    public static final int RECHARGE_ERROR = 3000000;
    public static final String RECHARGE_ERROR_MSG = "转账失败";

    public static final int PACKAGE_IS_NULL_ERROR = 3000001;
    public static final String PACKAGE_IS_NULL_ERROR_MSG = "套餐信息不存在";

    public static final int AMOUNT_NOT_ENOUGH_ERROR = 3000002;
    public static final String AMOUNT_NOT_ENOUGH_ERROR_MSG = "余额不足";

    public static final int RECHARGE_ORDER_IS_NULL_ERROR = 3000003;
    public static final String RECHARGE_ORDER_IS_NULL_ERROR_MSG = "充值订单不存在";

    public static final int PAY_AMOUNT_ERROR = 3000004;
    public static final String PAY_AMOUNT_ERROR_MSG = "支付金额错误";

    //代金券
    public static final int COUPON_IS_RECEIVED_ERROR = 4000000;
    public static final String COUPON_IS_RECEIVED_ERROR_MSG = "您已领过该代金券";

    public static final int COUPON_IS_NOT_ENOUGH_ERROR = 4000001;
    public static final String COUPON_IS_NOT_ENOUGH_ERROR_MSG = "代金券已被领完";

    public static final int RESTAURANT_REST_ERROR = 5000000;
    public static final String RESTAURANT_REST_ERROR_MSG = "店家休息";

    public static final int TABLE_NULL_ERROR = 6000000;
    public static final String TABLE_NULL_ERROR_MSG = "餐位不存在";

    public static final int TABLE_IS_USING_ERROR = 6000001;
    public static final String TABLE_IS_USING_ERROR_MSG = "该餐位已有人用餐";
}
