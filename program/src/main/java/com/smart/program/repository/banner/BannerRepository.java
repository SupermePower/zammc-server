package com.smart.program.repository.banner;

import com.smart.program.domain.banner.BannerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<BannerEntity, Long>, JpaSpecificationExecutor<BannerEntity> {

    /**
     * 获取banner集合
     *
     * @return
     * @throws Exception
     */
    @Query(value = "select banner from BannerEntity banner where banner.dataStatus = '0' and banner.bannerStatus = '1' order by banner.bannerSort")
    List<BannerEntity> queryBannerList() throws Exception;
}
