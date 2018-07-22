package com.smart.program.service.notice.impl;

import com.smart.program.domain.notice.NoticeEntity;
import com.smart.program.repository.notice.NoticeRepository;
import com.smart.program.response.notice.NoticeResponse;
import com.smart.program.service.notice.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    /**
     * 获取提示信息响应对象
     *
     * @return
     * @throws Exception
     */
    public List<NoticeResponse> queryNoticeResponse() throws Exception {
        List<NoticeEntity> noticeEntities = noticeRepository.queryNoticeList();
        List<NoticeResponse> noticeResponses = new ArrayList<>();
        for (NoticeEntity noticeEntity : noticeEntities) {
            NoticeResponse noticeResponse = new NoticeResponse();
            noticeResponse.setNotice(noticeEntity.getNoticeName());
            noticeResponse.setImgUrl(noticeEntity.getNoticeUrl());
            noticeResponses.add(noticeResponse);
        }
        return noticeResponses;
    }
}
