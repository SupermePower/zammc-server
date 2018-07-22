package com.smart.program.response.homepage;

import com.smart.program.response.notice.NoticeResponse;

import java.util.List;

public class HomePageResponse {

    /**banner信息*/
    private List<String> imgUrls;
    /**活动提示*/
    private List<NoticeResponse> notice;
    /**店家名称*/
    private String restaurantName;
    /**店家状态*/
    private Byte restaurantStatus;

    public HomePageResponse() {
    }

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public List<NoticeResponse> getNotice() {
        return notice;
    }

    public void setNotice(List<NoticeResponse> notice) {
        this.notice = notice;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Byte getRestaurantStatus() {
        return restaurantStatus;
    }

    public void setRestaurantStatus(Byte restaurantStatus) {
        this.restaurantStatus = restaurantStatus;
    }
}
