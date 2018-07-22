package com.smart.program.request.order;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;

public class OrderItemDTO {
    @NotNull(message = "商品id不能为空")
    private Long goodsId;
    @NotBlank(message = "商品类型不能为空")
    private String goodsType;
    @NotNull(message = "商品数量不能为空")
    private Integer goodsNum;
    @NotBlank(message = "商品名称不能为空")
    private String goodsName;
    @NotBlank(message = "商品原价不能为空")
    private BigDecimal goodsPrice;
    @NotNull(message = "商品实际价格不能为空")
    private BigDecimal realPrice;
    private Map<String,String> property;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
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

    public BigDecimal getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(BigDecimal realPrice) {
        this.realPrice = realPrice;
    }

    public Map<String, String> getProperty() {
        return property;
    }

    public void setProperty(Map<String, String> property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return "OrderItemDTO{" +
                "goodsId=" + goodsId +
                ", goodsType='" + goodsType + '\'' +
                ", goodsNum=" + goodsNum +
                ", goodsName='" + goodsName + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", realPrice=" + realPrice +
                ", property=" + property +
                '}';
    }
}
