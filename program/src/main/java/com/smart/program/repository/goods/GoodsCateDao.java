package com.smart.program.repository.goods;

import com.smart.program.domain.goods.GoodsCateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsCateDao extends JpaRepository<GoodsCateEntity, Long>,JpaSpecificationExecutor<GoodsCateEntity> {

}
