package com.smart.program.repository.goods;

import com.smart.program.domain.goods.GoodsPropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsPropertyDao extends JpaRepository<GoodsPropertyEntity, Long> ,JpaSpecificationExecutor<GoodsPropertyEntity> {

}
