package com.smart.program.service.order.impl;

import com.smart.program.common.ErrorConstant;
import com.smart.program.common.ObjectTranslate;
import com.smart.program.common.ServiceConfig;
import com.smart.program.common.StringUtil;
import com.smart.program.common.pay.PayUtil;
import com.smart.program.component.Printer;
import com.smart.program.domain.order.OrderInfoEntity;
import com.smart.program.domain.order.OrderItemEntity;
import com.smart.program.domain.recharge.RechargeOrderEntity;
import com.smart.program.domain.restaurant.RestaurantEntity;
import com.smart.program.exception.BusinessException;
import com.smart.program.idwork.IdWorker;
import com.smart.program.repository.order.OrderInfoDao;
import com.smart.program.repository.order.OrderItemDao;
import com.smart.program.repository.recharge.RechargeOrderRepository;
import com.smart.program.request.UserRequest;
import com.smart.program.request.order.OrderItemDTO2;
import com.smart.program.request.order.PayOrderRequest;
import com.smart.program.request.order.PlaceOrderRequest;
import com.smart.program.response.order.OrderResponse;
import com.smart.program.response.order.OrderResponseList;
import com.smart.program.response.order.PlaceOrderResponse;
import com.smart.program.service.order.OrderItemService;
import com.smart.program.service.order.OrderService;
import com.smart.program.service.restaurant.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private OrderInfoDao orderInfoDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private Printer printer;

    @Autowired
    private RechargeOrderRepository rechargeOrderRepository;

    /**
     * 获取用户订单信息
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public OrderResponseList queryUserOrder(UserRequest request) throws Exception {
        OrderResponseList orderResponseList = new OrderResponseList();

        RestaurantEntity restaurantEntity = restaurantService.queryRestaurant();

        //未支付订单
        List<OrderInfoEntity> unPayOrderList = orderInfoDao.findUnPayOrderByUserId(request);
        List<OrderResponse> unPayOrderResponseList = getOrderResponseList(restaurantEntity, unPayOrderList);
        orderResponseList.setPay(unPayOrderResponseList);

        //已支付订单
        List<OrderInfoEntity> payOrderList = orderInfoDao.findPayOrderByUserId(request);
        List<OrderResponse> payOrderResponseList = getOrderResponseList(restaurantEntity, payOrderList);
        orderResponseList.setFinish(payOrderResponseList);

        //已取消订单
        List<OrderInfoEntity> cancelOrderList = orderInfoDao.findCancelOrderByUserId(request);
        List<OrderResponse> cancelOrderResponseList = getOrderResponseList(restaurantEntity, cancelOrderList);
        orderResponseList.setCancel(cancelOrderResponseList);
        return orderResponseList;
    }

    /**
     * 获取订单信息
     *
     * @param restaurantEntity
     * @param unPayOrderList
     * @return
     * @throws Exception
     */
    private List<OrderResponse> getOrderResponseList(RestaurantEntity restaurantEntity, List<OrderInfoEntity> unPayOrderList) throws Exception {
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (OrderInfoEntity orderInfoEntity : unPayOrderList) {
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setRestaurantId(restaurantEntity.getRestaurantId());
            orderResponse.setRestaurantName(restaurantEntity.getRestaurantName());
            orderResponse.setRestaurantCode(restaurantEntity.getRestaurantCode());
            orderResponse.setRestaurantUrl(restaurantEntity.getRestaurantImg());
            orderResponse.setOrderId(orderInfoEntity.getOrderId());
            orderResponse.setOrderStatus(orderInfoEntity.getPayStatus());
            BigDecimal money = BigDecimal.ZERO;
            BigDecimal delMoney = BigDecimal.ZERO;
            BigDecimal actMoney = BigDecimal.ZERO;
            List<OrderItemEntity> orderItemEntities = orderItemService.queryOrderItem(orderInfoEntity.getOrderId());
            for (OrderItemEntity orderItemEntity : orderItemEntities) {
                money = money.add(orderItemEntity.getGoodsPrice());
                actMoney = actMoney.add(orderItemEntity.getRealPrice());
            }
            delMoney = money.subtract(actMoney);
            orderResponse.setMoney(money);
            orderResponse.setDelMoney(delMoney);
            orderResponse.setActMoney(actMoney);
            orderResponse.setCreateTime(orderInfoEntity.getCreateTime());
            orderResponses.add(orderResponse);
        }
        return orderResponses;
    }

    /**
     * 根据订单主键获取订单信息
     *
     * @param orderId
     * @return
     * @throws Exception
     */
    @Override
    public OrderInfoEntity queryOrderInfoById(long orderId) throws Exception {
        return orderInfoDao.findByOrderId(orderId);
    }

    /**
     * 用户下单
     *
     * @param request
     * @return
     */
    @Override
    public PlaceOrderResponse placeOrder(PlaceOrderRequest request) {
        List<OrderItemDTO2> goodMsg = request.getGoodMsg();
        long orderId = idWorker.nextId();

        BigDecimal totalPrice = BigDecimal.ZERO;
        //构建订单项
        List<OrderItemEntity> items = new ArrayList<>();
        for (OrderItemDTO2 orderItem : goodMsg) {
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setItemId(idWorker.nextId());
            orderItemEntity.setOrderId(orderId);
            orderItemEntity.setGoodsId(orderItem.getId());
            orderItemEntity.setGoodsName(orderItem.getName());
            orderItemEntity.setGoodsNum(orderItem.getNum());
            orderItemEntity.setGoodsPrice(orderItem.getPrice());
            orderItemEntity.setGoodsType(orderItem.getMemo());
            Integer num = orderItem.getNum();
            BigDecimal price = orderItem.getPrice().multiply(new BigDecimal(num));
            orderItemEntity.setRealPrice(price);
            orderItemEntity.setSubtotal(price);
            orderItemEntity.setOrderType((byte) 0);
            items.add(orderItemEntity);
            totalPrice = totalPrice.add(price);
        }

        //保存数据
        orderItemDao.saveAll(items);

        //构建订单
        OrderInfoEntity orderInfoEntity = getOrderInfoEntity(request, orderId, totalPrice);
        orderInfoDao.saveAndFlush(orderInfoEntity);

        //获取响应对象
        PlaceOrderResponse response = getPlaceOrderResponse(orderId, totalPrice);
        return response;
    }

    /**
     * 构建订单信息
     *
     * @param request    下单请求对象
     * @param orderId    订单主键
     * @param totalPrice 订单总价
     * @return
     */
    private OrderInfoEntity getOrderInfoEntity(PlaceOrderRequest request, long orderId, BigDecimal totalPrice) {
        OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
        orderInfoEntity.setOrderId(orderId);
        orderInfoEntity.setUserId(request.getUserId());
        orderInfoEntity.setTotalprice(totalPrice);
        orderInfoEntity.setPayStatus((byte) 0);
        orderInfoEntity.setTableNum(request.getTableCode());
        return orderInfoEntity;
    }

    /**
     * 获取下单响应对象
     *
     * @param orderId    订单ID
     * @param totalPrice 订单价格
     * @return
     */
    private PlaceOrderResponse getPlaceOrderResponse(long orderId, BigDecimal totalPrice) {
        PlaceOrderResponse response = new PlaceOrderResponse();
        response.setOrderId(orderId);
        response.setTotalPrice(totalPrice);
        return response;
    }

    /**
     * 获取打印数据
     *
     * @param orderItemEntities 订单详情
     * @return
     */
    private String getPrintContent(List<OrderItemEntity> orderItemEntities) {
        String content;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BigDecimal totalPrice = BigDecimal.ZERO;
        content = "<CB>点餐信息</CB><BR>";
        content += "名称　　　　　 单价  数量 金额<BR>";
        content += "--------------------------------<BR>";
        for (OrderItemEntity orderItem : orderItemEntities) {
            content += orderItem.getGoodsName() + "　　　　　　 " + orderItem.getRealPrice() + "    " + orderItem.getGoodsNum() + "   "
                    + orderItem.getRealPrice().multiply(new BigDecimal(orderItem.getGoodsNum())) + "<BR>";
            totalPrice.add(orderItem.getRealPrice());
        }
        content += "备注：<BR>";
        content += "--------------------------------<BR>";
        content += "合计：" + totalPrice + "元<BR>";
        content += "餐厅：北海渔村<BR>";
        content += "联系电话：13888888888888<BR>";
        content += "订餐时间：" + sdf.format(new Date()) + "<BR>";
        content += "<QR>http://www.sxmbyd.com</QR>";
        return content;
    }

    /**
     * 支付订单
     *
     * @param request 订单支付请求兑现
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> payOrder(PayOrderRequest request) throws Exception {
        OrderInfoEntity byOrderId = orderInfoDao.findByOrderId(request.getOrderId());
        byOrderId.setIsConfirm((byte) 1);
        byOrderId.setMemo(null == request.getMemo() ? "" : request.getMemo());
        orderInfoDao.saveAndFlush(byOrderId);
        BigDecimal totalprice = byOrderId.getTotalprice();
        //微信支付
        Map<String, Object> result = new HashMap<>();
        if (request.getPayWay() == 0) {
            Long orderId = idWorker.nextId();
            //生成的随机字符串
            String nonce_str = StringUtil.getRandomStringByLength(32);
            //组装参数，用户生成统一下单接口的签名
            Map<String, String> packageParams = getSign(byOrderId.getUserId(), orderId, totalprice, nonce_str);
            // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            String preStr = PayUtil.createLinkString(packageParams);
            //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
            String mySign = PayUtil.sign(preStr, ServiceConfig.key, "utf-8").toUpperCase();
            //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
            String xml = getMessage(orderId, totalprice, nonce_str, mySign);
            //调用统一下单接口，并接受返回的结果
            String res = PayUtil.httpRequest(ServiceConfig.pay_url, "POST", xml);
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

            RechargeOrderEntity rechargeOrderEntity = new RechargeOrderEntity();
            rechargeOrderEntity.setOrderId(orderId);
            rechargeOrderEntity.setUserId(byOrderId.getUserId());

            rechargeOrderEntity.setPayMoney(totalprice);
            rechargeOrderEntity.setIsPackage((byte) 0);
            // 新增充值信息至订单表
            rechargeOrderRepository.saveAndFlush(rechargeOrderEntity);
        }
        List<OrderItemEntity> orderItemEntities = orderItemDao.queryOrderItem(request.getOrderId());
        printer.print(Printer.SN, getPrintContent(orderItemEntities));
        return result;
    }

    /**
     * 获取签名
     *
     * @param userId    用户主键
     * @param orderId   订单主键
     * @param fee       支付金额
     * @param nonce_str nonce_str
     * @return
     */
    private Map<String, String> getSign(String userId, Long orderId, BigDecimal fee, String nonce_str) {
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
        packageParams.put("openid", userId);
        return packageParams;
    }

    /**
     * 获取报文
     *
     * @param orderId   订单主键
     * @param fee       订单价格
     * @param nonce_str nonce_str
     * @param mySign    签名信息
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
