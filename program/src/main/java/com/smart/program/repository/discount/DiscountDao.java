package com.smart.program.repository.discount;

import com.smart.program.domain.discount.DiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountDao extends JpaRepository<DiscountEntity, Long> {

}
