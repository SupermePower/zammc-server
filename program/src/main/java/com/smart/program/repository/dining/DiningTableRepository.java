package com.smart.program.repository.dining;

import com.smart.program.domain.dining.DiningTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @description 餐位仓库
 * @author: liying.fu
 * @Date: 2018/8/14 下午10:16
 */
@Repository
public interface DiningTableRepository extends JpaRepository<DiningTableEntity, Long> {
}
