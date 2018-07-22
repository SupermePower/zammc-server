package com.smart.program.service.activity.impl;

import com.smart.program.domain.activity.ActivityEntity;
import com.smart.program.repository.activity.ActivityRepository;
import com.smart.program.response.activity.ActivityResponse;
import com.smart.program.service.activity.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 活动业务实现
 * @author: fly
 * @Date: 2018/7/22 下午6:52
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    /**
     * 获取活动信息
     *
     * @return
     * @throws Exception
     */
    public List<ActivityResponse> queryActivity() throws Exception {
        List<ActivityEntity> activityEntities = activityRepository.queryActivity();
        List<ActivityResponse> activityResponses = new ArrayList<>();
        for (ActivityEntity activityEntity : activityEntities) {
            ActivityResponse activityResponse = new ActivityResponse();
            activityResponse.setActivityName(activityEntity.getActivityName());
            activityResponses.add(activityResponse);
        }
        return activityResponses;
    }
}