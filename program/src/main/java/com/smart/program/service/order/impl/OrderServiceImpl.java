package com.smart.program.service.order.impl;

import com.smart.program.component.Printer;
import com.smart.program.domain.order.OrderInfoEntity;
import com.smart.program.domain.order.OrderItemEntity;
import com.smart.program.domain.restaurant.RestaurantEntity;
import com.smart.program.idwork.IdWorker;
import com.smart.program.repository.goods.GoodsDao;
import com.smart.program.repository.order.OrderInfoDao;
import com.smart.program.repository.order.OrderItemDao;
import com.smart.program.request.UserRequest;
import com.smart.program.request.order.OrderItemDTO2;
import com.smart.program.request.order.PlaceOrderRequest;
import com.smart.program.response.order.OrderResponse;
import com.smart.program.response.order.OrderResponseList;
import com.smart.program.service.order.OrderItemService;
import com.smart.program.service.order.OrderService;
import com.smart.program.service.restaurant.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
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
    private GoodsDao goodsDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private Printer printer;

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
    public BigDecimal placeOrder(PlaceOrderRequest request) {
        List<OrderItemDTO2> goodMsg = request.getGoodMsg();
        long orderId = idWorker.nextId();
        List<OrderItemEntity> items = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        //构建订单项
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
        //构建订单
        OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
        orderInfoEntity.setOrderId(orderId);
        orderInfoEntity.setUserId(request.getUserId());
        orderInfoEntity.setTotalprice(totalPrice);
        orderInfoEntity.setPayStatus((byte) 0);
        //保存数据
        orderItemDao.saveAll(items);
        orderInfoDao.saveAndFlush(orderInfoEntity);

        printer.print(Printer.SN, getPrintContent(goodMsg));
        return totalPrice;
    }

    /**
     * 获取打印数据
     *
     * @param goodMsg
     * @return
     */
    private String getPrintContent(List<OrderItemDTO2> goodMsg) {
        String content;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BigDecimal totalPrice = BigDecimal.ZERO;
        content = "<CB>点餐信息</CB><BR>";
        content += "名称　　　　　 单价  数量 金额<BR>";
        content += "--------------------------------<BR>";
        for (OrderItemDTO2 orderItem : goodMsg) {
            content += orderItem.getName() + "　　　　　　 " + orderItem.getPrice() + "    " + orderItem.getNum() + "   "
                    + orderItem.getPrice().multiply(new BigDecimal(orderItem.getNum())) + "<BR>";
            totalPrice.add(orderItem.getPrice());
        }
        content += "备注：<BR>";
        content += "--------------------------------<BR>";
        content += "合计：" + totalPrice + "元<BR>";
        content += "餐厅：最爱妈妈菜<BR>";
        content += "联系电话：13888888888888<BR>";
        content += "订餐时间：" + sdf.format(new Date()) + "<BR>";
        content += "<QR>http://www.sxmbyd.com</QR>";
        return content;
    }
}
