package com.plaza.infrastructure.out.jpa.adapter;

import com.plaza.domain.model.OrderDishModel;
import com.plaza.domain.spi.IOrderDishPersistencePort;
import com.plaza.infrastructure.out.jpa.mapper.IOrderDishEntityMapper;
import com.plaza.infrastructure.out.jpa.repository.IOrderDishRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderDishJpaAdapter implements IOrderDishPersistencePort {

    private final IOrderDishRepository orderDishRepository;
    private final IOrderDishEntityMapper orderDishEntityMapper;

    @Override
    public List<OrderDishModel> getOrderDishes(Integer idOrder) {
        return orderDishRepository.findByDish_Id(idOrder).stream().map(orderDishEntityMapper::toModel).toList();
    }
}
