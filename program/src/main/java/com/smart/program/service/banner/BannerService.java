package com.smart.program.service.banner;

import java.util.List;

public interface BannerService {

    /**
     * 获取banner响应对象
     * @return
     * @throws Exception
     */
    List<String> queryBannerResponse() throws Exception;
}
