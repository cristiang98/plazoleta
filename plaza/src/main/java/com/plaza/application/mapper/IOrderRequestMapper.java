package com.plaza.application.mapper;

import com.plaza.application.dto.request.OrderDishRequestDto;
import com.plaza.application.dto.request.OrderRequestDto;
import com.plaza.application.dto.request.UpdateOrderRequestDto;
import com.plaza.domain.model.DishModel;
import com.plaza.domain.model.OrderDishModel;
import com.plaza.domain.model.OrderModel;
import com.plaza.domain.model.RestaurantModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IOrderRequestMapper {

    @Mapping(target = "restaurant", source = "idRestaurant")
    @Mapping(target = "orderDishes", source = "orderDishes")
    OrderModel toModel(OrderRequestDto orderRequestDto);

    @Mapping(target = "id", source = "idOrder")
    @Mapping(target = "status", source = "status")
    OrderModel toModelFromUpdate(UpdateOrderRequestDto updateOrderRequestDto);


    default RestaurantModel toRestaurant(Integer restaurantId) {
        RestaurantModel restaurant = new RestaurantModel();
        restaurant.setNit(restaurantId);
        return restaurant;
    }

    default List<OrderDishModel> toOrderDish(List<OrderDishRequestDto> orderDishesRequest) {
        return orderDishesRequest.stream().map(orderDishRequest -> {
            DishModel dish = new DishModel();
            dish.setId(orderDishRequest.getIdDish());

            OrderDishModel orderDish = new OrderDishModel();
            orderDish.setDish(dish);
            orderDish.setQuantity(orderDishRequest.getQuantity());

            return orderDish;
        }).collect(Collectors.toList());
    }

}
