package com.smart.program.common;

public class ServiceConfig {
	
	//小程序appid
	public static final String MINIAPPID = "wxee3f46cc7f8ca2dd";

	//微信支付的商户id
	public static final String mch_id = "1504884031";

	//微信支付的商户密钥
	public static final String key = "shanximanbuyundiankejiyouxiang11";

	//支付成功后的服务器回调url
	public static final String notify_url = "https://www.sxmbyd.com/order-foods/recharge/callBack";

	//交易类型，小程序支付的固定值为JSAPI
	public static final String TRADETYPE = "JSAPI";
	//微信统一下单接口地址
	public static final String pay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	//企业付款-提现
	public static final String withdrawals_url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

	public static final String cert_path = "/usr/local/fulishe/cert/apiclient_cert.p12";

}
