package com.smart.program.service.banner.impl;

import com.smart.program.domain.banner.BannerEntity;
import com.smart.program.repository.banner.BannerRepository;
import com.smart.program.service.banner.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    /**
     * 获取banner响应对象
     *
     * @return
     * @throws Exception
     */
    public List<String> queryBannerResponse() throws Exception {
        List<BannerEntity> bannerEntities = bannerRepository.queryBannerList();
        List<String> bannerResponses = new ArrayList<>();
        for (BannerEntity bannerEntity : bannerEntities) {
            bannerResponses.add(bannerEntity.getBannerUrl());
        }
        return bannerResponses;
    }
}
