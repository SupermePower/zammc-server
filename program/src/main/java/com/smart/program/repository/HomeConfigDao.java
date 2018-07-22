package com.smart.program.repository;

import com.smart.program.domain.HomeConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeConfigDao extends JpaRepository<HomeConfigEntity, Long> ,JpaSpecificationExecutor<HomeConfigEntity> {

}
