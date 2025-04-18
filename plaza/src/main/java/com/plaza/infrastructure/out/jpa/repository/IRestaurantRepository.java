package com.plaza.infrastructure.out.jpa.repository;

import com.plaza.domain.model.RestaurantModel;
import com.plaza.infrastructure.out.jpa.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRestaurantRepository extends JpaRepository<RestaurantEntity, Integer> {

    RestaurantEntity findByName(String name);

    RestaurantEntity findByIdOwner(Integer idOwner);

    Optional<RestaurantEntity> findById(Integer id);
}
