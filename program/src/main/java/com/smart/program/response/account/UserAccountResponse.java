package com.smart.program.response.account;

import java.math.BigDecimal;

public class UserAccountResponse {

    private BigDecimal amount;

    public UserAccountResponse() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
