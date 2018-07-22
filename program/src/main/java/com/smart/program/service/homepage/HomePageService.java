package com.smart.program.service.homepage;

import com.smart.program.response.homepage.HomePageResponse;

public interface HomePageService {

    /**
     * 查询首页信息
     *
     * @return
     * @throws Exception
     */
    HomePageResponse queryHomePage() throws Exception;
}
