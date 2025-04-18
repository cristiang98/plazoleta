package com.plaza.infrastructure.out.jpa.repository;

import com.plaza.infrastructure.out.jpa.entity.RestaurantEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRestaurantEmployeeRepository extends JpaRepository<RestaurantEmployeeEntity, Integer> {

    RestaurantEmployeeEntity findByEmployeeId(Integer employeeId);

}
