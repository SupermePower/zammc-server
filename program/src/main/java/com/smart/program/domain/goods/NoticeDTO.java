package com.smart.program.domain.goods;

import com.smart.program.domain.notice.NoticeEntity;
import com.smart.program.response.notice.NoticeResponse;
import lombok.Data;

/**
 * com.smart.program.domain.goods
 * Created by baihe
 * ON 2018/5/23.
 */
@Data
public class NoticeDTO {
    private String imgUrl;
    private String notice;

    public NoticeDTO (NoticeEntity entity){
        this.imgUrl=entity.getNoticeUrl();
        this.notice=entity.getNoticeName();
    }

    public NoticeDTO() {
    }
}
