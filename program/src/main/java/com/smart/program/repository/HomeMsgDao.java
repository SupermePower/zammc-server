package com.smart.program.repository;

import com.smart.program.domain.HomeMsgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeMsgDao extends JpaRepository<HomeMsgEntity, Long>,JpaSpecificationExecutor<HomeMsgEntity> {

}
