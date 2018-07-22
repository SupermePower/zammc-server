package com.smart.program.repository.restaurant;

import com.smart.program.domain.restaurant.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

    @Query(value = "select restaurant from RestaurantEntity restaurant where restaurant.dataStatus = '0'")
    RestaurantEntity queryRestaurantEntity() throws Exception;
}
