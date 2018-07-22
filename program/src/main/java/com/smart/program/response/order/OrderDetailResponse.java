package com.smart.program.response.order;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderDetailResponse {

    /**商品主键*/
    private Long goodsId;
    /**商品名称*/
    private String goodsName;
    /**商品价格*/
    private BigDecimal goodsPrice;
    /**商品总价*/
    private BigDecimal subtotal;
    /**下单时间*/
    private Timestamp createTime;

    public OrderDetailResponse() {
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
