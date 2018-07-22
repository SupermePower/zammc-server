package com.smart.program.response.account;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class UserAccountWaterResponse {


    private Byte waterType;
    private BigDecimal waterNum;
    private Timestamp waterTime;

    public UserAccountWaterResponse() {
    }

    public Byte getWaterType() {
        return waterType;
    }

    public void setWaterType(Byte waterType) {
        this.waterType = waterType;
    }

    public BigDecimal getWaterNum() {
        return waterNum;
    }

    public void setWaterNum(BigDecimal waterNum) {
        this.waterNum = waterNum;
    }

    public Timestamp getWaterTime() {
        return waterTime;
    }

    public void setWaterTime(Timestamp waterTime) {
        this.waterTime = waterTime;
    }
}
