package com.smart.program.repository.activity;

import com.smart.program.domain.activity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description 活动数据仓库
 * @author: fly
 * @Date: 2018/7/22 下午6:55
 */
@Repository
public interface ActivityRepository extends JpaRepository<ActivityEntity, Long> {

    /**
     * 获取活动信息
     *
     * @return
     * @throws Exception
     */
    @Query(value = "select activity from ActivityEntity activity where activity.dataStatus = '0' and activity.status = '0'")
    List<ActivityEntity> queryActivity() throws Exception;
}
