package com.smart.program.service.activity;

import com.smart.program.response.activity.ActivityResponse;

import java.util.List;

/**
 * @description 活动业务接口
 * @author: fly
 * @Date: 2018/7/22 下午6:51
 */
public interface ActivityService {

    /**
     * 获取活动信息
     *
     * @return
     * @throws Exception
     */
    List<ActivityResponse> queryActivity() throws Exception;
}
