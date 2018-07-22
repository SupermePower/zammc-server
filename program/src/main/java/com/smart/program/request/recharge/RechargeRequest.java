package com.smart.program.request.recharge;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 用户充值请求对象
 */
public class RechargeRequest {

    @NotNull(message = "用户不能为空")
    private String userId;
    private Long packageId;
    private BigDecimal rechargeMoney;
    @NotNull(message = "是否是套餐不能为空")
    private Byte isPackage;

    public RechargeRequest() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public BigDecimal getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(BigDecimal rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public Byte getIsPackage() {
        return isPackage;
    }

    public void setIsPackage(Byte isPackage) {
        this.isPackage = isPackage;
    }

    @Override
    public String toString() {
        return "RechargeRequest{" +
                "userId='" + userId + '\'' +
                ", packageId=" + packageId +
                ", rechargeMoney=" + rechargeMoney +
                ", isPackage=" + isPackage +
                '}';
    }
}
