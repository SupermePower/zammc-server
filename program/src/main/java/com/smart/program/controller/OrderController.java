package com.smart.program.controller;

import com.smart.program.common.ErrorConstant;
import com.smart.program.request.UserRequest;
import com.smart.program.request.order.PlaceOrderRequest;
import com.smart.program.request.order.QueryOrderDetailRequest;
import com.smart.program.response.ResponseVO;
import com.smart.program.response.order.OrderDetailResponseList;
import com.smart.program.response.order.OrderResponseList;
import com.smart.program.response.order.PlaceOrderResponse;
import com.smart.program.service.order.OrderItemService;
import com.smart.program.service.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    /**
     * 获取订单信息
     *
     * @param request
     * @return
     */
    @RequestMapping(path = "/queryUserOrder", method = RequestMethod.POST)
    public ResponseVO<OrderResponseList> queryUserOrder(@RequestBody @Valid UserRequest request) {
        ResponseVO<OrderResponseList> responseVO = new ResponseVO<>();
        try {
            OrderResponseList orderResponseList = orderService.queryUserOrder(request);
            responseVO.setResult(ErrorConstant.SUCCESS_CODE, ErrorConstant.SUCCESS_MSG, orderResponseList);
        } catch (Exception e) {
            log.error("OrderController queryUserOrder -> {} Exception \n", request.toString(), e);
            responseVO.setResult(ErrorConstant.ERROR_CODE, ErrorConstant.ERROR_MSG);
        }
        return responseVO;
    }

    /**
     * 获取订单详情信息
     *
     * @param request
     * @return
     */
    @RequestMapping(path = "/queryOrderDetail", method = RequestMethod.POST)
    public ResponseVO<OrderDetailResponseList> queryOrderDetail(@RequestBody @Valid QueryOrderDetailRequest request) {
        ResponseVO<OrderDetailResponseList> responseVO = new ResponseVO<>();
        try {
            OrderDetailResponseList orderDetailResponse = orderItemService.queryOrderDetail(request);
            responseVO.setResult(ErrorConstant.SUCCESS_CODE, ErrorConstant.SUCCESS_MSG, orderDetailResponse);
        } catch (Exception e) {
            log.error("OrderController queryOrderDetail request -> {} Exception \n", request.toString(), e);
            responseVO.setResult(ErrorConstant.ERROR_CODE, ErrorConstant.ERROR_MSG);
        }
        return responseVO;
    }

    /**
     * 用户下单
     *
     * @param request
     * @return
     */
    @RequestMapping(path = "/placeOrder", method = RequestMethod.POST)
    public ResponseVO<PlaceOrderResponse> placeOrder(@RequestBody @Valid PlaceOrderRequest request) {
        log.info("place order param -> {}", request.toString());
        ResponseVO<PlaceOrderResponse> responseVO = new ResponseVO<>();
        try {
            PlaceOrderResponse response = orderService.placeOrder(request);
            responseVO.setResult(ErrorConstant.SUCCESS_CODE, ErrorConstant.SUCCESS_MSG, response);
        } catch (Exception e) {
            log.error("OrderController queryUserOrder -> {} Exception \n", request.toString(), e);
            responseVO.setResult(ErrorConstant.ERROR_CODE, ErrorConstant.ERROR_MSG);
        }
        return responseVO;
    }
}
