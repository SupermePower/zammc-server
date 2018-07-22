package com.smart.program.service.recharge.impl;

import com.smart.program.common.ErrorConstant;
import com.smart.program.common.ObjectTranslate;
import com.smart.program.common.ServiceConfig;
import com.smart.program.common.StringUtil;
import com.smart.program.common.pay.PayUtil;
import com.smart.program.domain.recharge.RechargeOrderEntity;
import com.smart.program.domain.recharge.RechargePackageEntity;
import com.smart.program.exception.BusinessException;
import com.smart.program.idwork.IdWorker;
import com.smart.program.repository.recharge.RechargeOrderRepository;
import com.smart.program.repository.recharge.RechargePackageRepository;
import com.smart.program.request.account.UserAccountService;
import com.smart.program.request.recharge.RechargeRequest;
import com.smart.program.response.recharge.RechargePackageResponse;
import com.smart.program.service.recharge.RechargeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RechargeServiceImpl implements RechargeService {

    @Autowired
    private RechargePackageRepository rechargePackageRepository;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RechargeOrderRepository rechargeOrderRepository;

    @Autowired
    private UserAccountService userAccountService;

    /**
     * 获取充值套餐
     *
     * @return
     * @throws Exception
     */
    public List<RechargePackageResponse> queryRechargePackage() throws Exception {
        List<RechargePackageResponse> rechargePackageResponses = new ArrayList<>();
        List<RechargePackageEntity> rechargePackageEntities = rechargePackageRepository.queryRechargePackageList();
        for (RechargePackageEntity rechargePackageEntity : rechargePackageEntities) {
            RechargePackageResponse rechargePackageResponse = new RechargePackageResponse();
            rechargePackageResponse.setPackageId(rechargePackageEntity.getPackageId());
            rechargePackageResponse.setRechargeMoney(rechargePackageEntity.getRechargeMoney());
            rechargePackageResponse.setPayMoney(rechargePackageEntity.getPayMoney());
            rechargePackageResponse.setChosen(false);
            rechargePackageResponses.add(rechargePackageResponse);
        }
        return rechargePackageResponses;
    }

    /**
     * 充值
     *
     * @param request
     * @throws Exception
     */
    public synchronized Map<String, Object> recharge(RechargeRequest request) throws Exception {
        RechargeOrderEntity rechargeOrderEntity = new RechargeOrderEntity();
        Long orderId = idWorker.nextId();
        rechargeOrderEntity.setOrderId(orderId);
        rechargeOrderEntity.setUserId(request.getUserId());

        BigDecimal fee = BigDecimal.ZERO;
        if (request.getIsPackage() == 1) {//是套餐
            // 获取套餐信息
            RechargePackageEntity rechargePackageEntity = rechargePackageRepository.queryPackageById(request.getPackageId());
            if (null == rechargeOrderEntity) {
                throw new BusinessException(ErrorConstant.PACKAGE_IS_NULL_ERROR, ErrorConstant.PACKAGE_IS_NULL_ERROR_MSG);
            }
            fee = rechargePackageEntity.getPayMoney();
            rechargeOrderEntity.setRechargeMoney(rechargePackageEntity.getRechargeMoney());
        } else {
            fee = request.getRechargeMoney();
            rechargeOrderEntity.setRechargeMoney(fee);
        }
        rechargeOrderEntity.setPayMoney(fee);
        rechargeOrderEntity.setIsPackage(request.getIsPackage());
        // 新增充值信息至订单表
        rechargeOrderRepository.saveAndFlush(rechargeOrderEntity);

        //生成的随机字符串
        String nonce_str = StringUtil.getRandomStringByLength(32);

        //组装参数，用户生成统一下单接口的签名
        Map<String, String> packageParams = getSign(request, orderId, fee, nonce_str);

        String preStr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

        //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
        String mySign = PayUtil.sign(preStr, ServiceConfig.key, "utf-8").toUpperCase();

        //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
        String xml = getMessage(orderId, fee, nonce_str, mySign);

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

        String return_code = (String) map.get("return_code");//返回状态码

        if (return_code == "SUCCESS") {
            String prepay_id = (String) map.get("prepay_id");//返回的预付单信息
            result.put("nonceStr", nonce_str);
            result.put("package", "prepay_id=" + prepay_id);
            Long timeStamp = System.currentTimeMillis() / 1000;
            result.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
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

    /**
     * 获取签名
     *
     * @param request
     * @param orderId
     * @param fee
     * @param nonce_str
     * @return
     */
    private Map<String, String> getSign(RechargeRequest request, Long orderId, BigDecimal fee, String nonce_str) {
        Map<String, String> packageParams = new HashMap<>();
        packageParams.put("appid", ServiceConfig.MINIAPPID);
        packageParams.put("mch_id", ServiceConfig.mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", "转账");
        packageParams.put("out_trade_no", ObjectTranslate.getString(orderId));//商户订单号
        packageParams.put("total_fee", fee + "");//支付金额，这边需要转成字符串类型，否则后面的签名会失败  ObjectTranslate.getString(order.get("transport"))
        packageParams.put("spbill_create_ip", "127.0.0.1");
        packageParams.put("notify_url", ServiceConfig.notify_url);//支付成功后的回调地址
        packageParams.put("trade_type", ServiceConfig.TRADETYPE);//支付方式
        packageParams.put("openid", request.getUserId());
        return packageParams;
    }

    /**
     * 支付回调
     *
     * @param request
     * @return
     * @throws Exception
     */
    public String getCallBack(HttpServletRequest request) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        // sb为微信返回的xml
        String notityXml = sb.toString();
        String resXml = "";
        log.info("接收到的报文 -> {}", notityXml);

        Map map = PayUtil.doXMLParse(notityXml);

        String return_code = map.get("return_code").toString().toUpperCase();

        if (return_code.equals("SUCCESS")) {
            //进行签名验证，看是否是从微信发送过来的，防止资金被盗
            System.err.println();
            // 验证签名是否正确
            if (PayUtil.checkSign(notityXml)) {
                // 订单支付成功 业务处理
                String out_trade_no = ObjectTranslate.getString(map.get("out_trade_no")); // 商户订单号
                // 修改订单状态
                updateOrderFinish(out_trade_no);
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                // 修改账户信息
                updateUserAccount(Long.parseLong(out_trade_no));
            }
        } else {
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        log.info("callBack xml -> {}", resXml);
        log.info("微信支付回调数据结束");
        return resXml;
    }

    /**
     * 修改订单状态为已完成
     *
     * @param orderId
     * @throws Exception
     */
    private void updateOrderFinish(String orderId) throws Exception {
        rechargeOrderRepository.updateOrderFinish(Long.parseLong(orderId));
    }

    /**
     * 支付成功后修改账户信息
     *
     * @param orderId
     * @throws Exception
     */
    private void updateUserAccount(Long orderId) throws Exception {
        userAccountService.updateUserAccount(orderId);
    }

}
