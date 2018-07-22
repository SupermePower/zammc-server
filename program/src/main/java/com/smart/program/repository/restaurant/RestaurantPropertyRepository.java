package com.smart.program.repository.restaurant;

import com.smart.program.domain.restaurant.RestaurantPropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantPropertyRepository extends JpaRepository<RestaurantPropertyEntity, Long> ,JpaSpecificationExecutor<RestaurantPropertyEntity> {

}
