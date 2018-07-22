package com.smart.program.request.reserve;

import javax.validation.constraints.NotNull;

/**
 * 用户预约请求参数对象
 */
public class UserReserveRequest {

    @NotNull(message = "姓名必须填写")
    private String realName;
    @NotNull(message = "联系电话必须填写")
    private String mobile;
    @NotNull(message = "用餐人数必须填写")
    private Integer reserveNumbers;
    @NotNull(message = "预定时间必须填写")
    private String  currentTime;
    @NotNull(message = "预定时间必须填写")
    private String currentDate;
    private String summary;

    public UserReserveRequest() {
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getReserveNumbers() {
        return reserveNumbers;
    }

    public void setReserveNumbers(Integer reserveNumbers) {
        this.reserveNumbers = reserveNumbers;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "UserReserveRequest{" +
                "realName='" + realName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", reserveNumbers=" + reserveNumbers +
                ", currentTime='" + currentTime + '\'' +
                ", currentDate='" + currentDate + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
