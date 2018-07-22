package com.smart.program.service.notice;

import com.smart.program.response.notice.NoticeResponse;

import java.util.List;

public interface NoticeService {

    /**
     * 获取提示信息响应对象
     *
     * @return
     * @throws Exception
     */
    List<NoticeResponse> queryNoticeResponse() throws Exception;
}
