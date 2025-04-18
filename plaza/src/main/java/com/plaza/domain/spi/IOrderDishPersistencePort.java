package com.plaza.domain.spi;

import com.plaza.domain.model.OrderDishModel;

import java.util.List;

public interface IOrderDishPersistencePort {

    List<OrderDishModel> getOrderDishes(Integer idOrder);

}
