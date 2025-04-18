package com.plaza.application.handler.impl;

import com.plaza.application.dto.request.RestaurantRequestDto;
import com.plaza.application.dto.response.RestaurantResponseDto;
import com.plaza.application.exception.RestaurantExistException;
import com.plaza.application.handler.IRestaurantHandler;
import com.plaza.application.mapper.IRestaurantRequestMapper;
import com.plaza.application.mapper.response.IRestaurantResponseMapper;
import com.plaza.domain.api.IRestaurantServicePort;
import com.plaza.domain.model.RestaurantModel;
import com.plaza.domain.spi.IRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantHandlerImp implements IRestaurantHandler {

    private final IRestaurantServicePort restaurantServicePort;
    @Qualifier("IRestaurantRequestMapper")
    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IRestaurantResponseMapper restaurantResponseMapper;

    @Override
    public void saveRestaurant(RestaurantRequestDto restaurantRequestDto) {

        if(Boolean.TRUE.equals(restaurantPersistencePort.existsRestaurant(restaurantRequestDto.getNit()))){
            throw new RestaurantExistException("El restaurante ya existe");
        }
        RestaurantModel restaurantModel = restaurantRequestMapper.toRestaurantModel(restaurantRequestDto);
        restaurantServicePort.saveRestaurant(restaurantModel);

    }

    @Override
    public Page<RestaurantResponseDto> getRestaurants(int page, int size) {

        return restaurantServicePort.getRestaurants(page, size).map(restaurantResponseMapper::toRestaurantResponseDto);
    }
}
