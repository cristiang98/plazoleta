package com.plaza.infrastructure.out.jpa.repository;

import com.plaza.infrastructure.out.jpa.entity.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDishRepository extends JpaRepository<DishEntity, Integer> {

    Page<DishEntity> findByCategoryName(Integer nit, String categoryFilter, Pageable pageable);

    Page<DishEntity> findByRestaurantNit(Integer nit, Pageable pageable);
}
