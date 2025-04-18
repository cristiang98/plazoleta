package com.plaza.infrastructure.out.jpa.repository;

import com.plaza.infrastructure.out.jpa.entity.OrderEntity;
import com.plaza.infrastructure.out.jpa.entity.enums.OrderStatusI;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.stream.Collectors;

public interface IOrderRepository extends JpaRepository<OrderEntity, Integer> {

    List<OrderEntity> findByIdClient(Integer idClient);

    Page<OrderEntity> findAllByStatusAndRestaurant_Nit(String status, Integer restaurantNit, Pageable pageable);

    List<OrderEntity> findByRestaurant_Nit(Integer restaurantNit);

    Long countAllByIdClientAndStatusIn(Integer idClient, List<OrderStatusI> statusList);


    Page<OrderEntity> findByStatusAndRestaurant_Nit(OrderStatusI status, Integer restaurantNit, Pageable pageable);

    long countAllByStatusAndRestaurantNit(OrderStatusI orderStatusI, Integer restaurantNit);

    List<OrderEntity> findAllByRestaurant_Nit(Integer restaurantNit);

}
