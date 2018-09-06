package com.smart.program.service.pay.impl;

import com.smart.program.common.ErrorConstant;
import com.smart.program.common.ObjectTranslate;
import com.smart.program.common.ServiceConfig;
import com.smart.program.common.StringUtil;
import com.smart.program.common.pay.PayUtil;
import com.smart.program.domain.recharge.RechargeOrderEntity;
import com.smart.program.exception.BusinessException;
import com.smart.program.idwork.IdWorker;
import com.smart.program.repository.recharge.RechargeOrderRepository;
import com.smart.program.request.pay.PayRequest;
import com.smart.program.service.pay.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RechargeOrderRepository rechargeOrderRepository;

    /**
     * 转账
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> pay(PayRequest request) throws Exception {

        BigDecimal payAmount = request.getPayAmount();
        if (null == payAmount || payAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(ErrorConstant.PAY_AMOUNT_ERROR, ErrorConstant.PAY_AMOUNT_ERROR_MSG);
        }

        RechargeOrderEntity rechargeOrderEntity = new RechargeOrderEntity();
        Long orderId = idWorker.nextId();
        rechargeOrderEntity.setOrderId(orderId);
        rechargeOrderEntity.setUserId(request.getUserId());

        rechargeOrderEntity.setPayMoney(payAmount);
        rechargeOrderEntity.setIsPackage((byte) 0);
        // 新增充值信息至订单表
        rechargeOrderRepository.saveAndFlush(rechargeOrderEntity);

        //生成的随机字符串
        String nonce_str = StringUtil.getRandomStringByLength(32);

        //组装参数，用户生成统一下单接口的签名
        Map<String, String> packageParams = getSign(request, orderId, request.getPayAmount(), nonce_str);

        // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String preStr = PayUtil.createLinkString(packageParams);

        //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
        String mySign = PayUtil.sign(preStr, ServiceConfig.key, "utf-8").toUpperCase();

        //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
        String xml = getMessage(orderId, payAmount, nonce_str, mySign);

        log.info("调试模式_统一下单接口 请求XML数据：-> {}", xml);

        //调用统一下单接口，并接受返回的结果
        String res = PayUtil.httpRequest(ServiceConfig.pay_url, "POST", xml);

        System.out.println("调试模式_统一下单接口 返回XML数据：" + res);

        Map<String, Object> result = new HashMap<>();

        // 将解析结果存储在HashMap中
        Map map = new HashMap();
        try {
            map = PayUtil.doXMLParse(res);
        } catch (Exception e) {
            log.error("调用微信转账失败");
            throw new BusinessException(ErrorConstant.RECHARGE_ERROR, ErrorConstant.RECHARGE_ERROR_MSG);
        }
        //返回状态码
        String return_code = (String) map.get("return_code");

        if (return_code == "SUCCESS") {
            //返回的预付单信息
            String prepay_id = (String) map.get("prepay_id");
            result.put("nonceStr", nonce_str);
            result.put("package", "prepay_id=" + prepay_id);
            Long timeStamp = System.currentTimeMillis() / 1000;
            //这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
            result.put("timeStamp", timeStamp + "");
            //拼接签名需要的参数
            String stringSignTemp = "appId=" + ServiceConfig.MINIAPPID + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id + "&signType=MD5&timeStamp=" + timeStamp;
            //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
            String paySign = PayUtil.sign(stringSignTemp, ServiceConfig.key, "utf-8").toUpperCase();
            result.put("nonce_str", nonce_str);
            result.put("package", "prepay_id=" + prepay_id);
            result.put("timeStamp", timeStamp + "");
            result.put("paySign", paySign);
        }
        return result;
    }

    /**
     * 获取签名
     *
     * @param request
     * @param orderId
     * @param fee
     * @param nonce_str
     * @return
     */
    private Map<String, String> getSign(PayRequest request, Long orderId, BigDecimal fee, String nonce_str) {
        Map<String, String> packageParams = new HashMap<>();
        packageParams.put("appid", ServiceConfig.MINIAPPID);
        packageParams.put("mch_id", ServiceConfig.mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", "转账");
        //商户订单号
        packageParams.put("out_trade_no", ObjectTranslate.getString(orderId));
        //支付金额，这边需要转成字符串类型，否则后面的签名会失败  ObjectTranslate.getString(order.get("transport"))
        packageParams.put("total_fee", fee + "");
        packageParams.put("spbill_create_ip", "127.0.0.1");
        //支付成功后的回调地址
        packageParams.put("notify_url", ServiceConfig.notify_url);
        //支付方式
        packageParams.put("trade_type", ServiceConfig.TRADETYPE);
        packageParams.put("openid", request.getUserId());
        return packageParams;
    }

    /**
     * 获取报文
     *
     * @param orderId
     * @param fee
     * @param nonce_str
     * @param mySign
     * @return
     */
    private String getMessage(Long orderId, BigDecimal fee, String nonce_str, String mySign) {
        return "<xml>" + "<appid>" + ServiceConfig.MINIAPPID + "</appid>"
                + "<body><![CDATA[\"转账\"]]></body>"
                + "<mch_id>" + ServiceConfig.mch_id + "</mch_id>"
                + "<nonce_str>" + nonce_str + "</nonce_str>"
                + "<notify_url>" + ServiceConfig.notify_url + "</notify_url>"
                + "<openid>" + ObjectTranslate.getString(orderId) + "</openid>"
                + "<out_trade_no>" + ObjectTranslate.getString(orderId) + "</out_trade_no>"
                + "<spbill_create_ip>127.0.0.1</spbill_create_ip>"
                + "<total_fee>" + fee + "</total_fee>"
                + "<trade_type>" + ServiceConfig.TRADETYPE + "</trade_type>"
                + "<sign>" + mySign + "</sign>"
                + "</xml>";
    }
}
