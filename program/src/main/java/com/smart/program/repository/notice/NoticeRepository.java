package com.smart.program.repository.notice;

import com.smart.program.domain.notice.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long>, JpaSpecificationExecutor<NoticeEntity> {

    /**
     * 获取提示信息集合
     *
     * @return
     * @throws Exception
     */
    @Query(value = "select notice from NoticeEntity notice where notice.dataStatus = '0' and notice.noticeStatus = '1' order by notice.noticeSort")
    List<NoticeEntity> queryNoticeList() throws Exception;
}
