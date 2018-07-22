package com.smart.program.repository.goods;

import com.smart.program.domain.goods.GoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsDao extends JpaRepository<GoodsEntity, Long> ,JpaSpecificationExecutor<GoodsEntity> {

}
