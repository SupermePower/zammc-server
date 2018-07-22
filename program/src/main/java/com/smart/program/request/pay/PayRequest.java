package com.smart.program.request.pay;

import com.smart.program.request.UserRequest;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PayRequest extends UserRequest {

    @NotNull(message = "转账金额不能为空")
    private BigDecimal payAmount;

    public PayRequest() {
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    @Override
    public String toString() {
        return "PayRequest{" +
                "payAmount=" + payAmount +
                '}';
    }
}
