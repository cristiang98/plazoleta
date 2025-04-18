package com.plaza.infrastructure.out.jpa.repository;

import com.plaza.infrastructure.out.jpa.entity.OrderDishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderDishRepository extends JpaRepository<OrderDishEntity, Integer> {

    List<OrderDishEntity> findByDish_Id(Integer dishId);
    List<OrderDishEntity> findByOrder_Id(Integer orderId);

}
