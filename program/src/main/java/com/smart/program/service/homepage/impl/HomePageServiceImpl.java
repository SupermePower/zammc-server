package com.smart.program.service.homepage.impl;

import com.smart.program.domain.restaurant.RestaurantEntity;
import com.smart.program.response.homepage.HomePageResponse;
import com.smart.program.response.notice.NoticeResponse;
import com.smart.program.service.banner.BannerService;
import com.smart.program.service.homepage.HomePageService;
import com.smart.program.service.notice.NoticeService;
import com.smart.program.service.restaurant.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomePageServiceImpl implements HomePageService {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private RestaurantService restaurantService;

    /**
     * 获取首页信息
     *
     * @return
     * @throws Exception
     */
    @Override
    public HomePageResponse queryHomePage() throws Exception {
        HomePageResponse homePageResponse = new HomePageResponse();
        // 获取banner信息
        List<String> bannerResponses = bannerService.queryBannerResponse();
        homePageResponse.setImgUrls(bannerResponses);
        // 获取提示信息
        List<NoticeResponse> noticeResponses = noticeService.queryNoticeResponse();
        homePageResponse.setNotice(noticeResponses);
        //获取店铺信息
        RestaurantEntity restaurantEntity = restaurantService.queryRestaurant();
        homePageResponse.setRestaurantName(restaurantEntity.getRestaurantName());
        homePageResponse.setRestaurantStatus(restaurantEntity.getStatus());
        return homePageResponse;
    }
}
