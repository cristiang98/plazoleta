package com.plaza.application.handler.impl;

import com.plaza.application.dto.request.RestaurantEmpRequestDto;
import com.plaza.application.handler.IRestaurantEmployeeHanlder;
import com.plaza.application.mapper.IRestaurantEmplRequestMapper;
import com.plaza.domain.api.IRestaurantEmployeeServicePort;
import com.plaza.domain.model.RestaurantEmployeeModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantEmployeeHandler implements IRestaurantEmployeeHanlder {

    private final IRestaurantEmployeeServicePort restaurantEmployeeServicePort;
    @Qualifier("IRestaurantEmplRequestMapper")
    private final IRestaurantEmplRequestMapper restaurantEmplRequestMapper;

    @Override
    public void saveEmployeeRestaurant(RestaurantEmpRequestDto restaurantEmpRequestDto, String token) {
        RestaurantEmployeeModel restaurantEmployeeModel = restaurantEmplRequestMapper.toModel(restaurantEmpRequestDto);
        restaurantEmployeeServicePort.saveEmployeeRestaurant(restaurantEmployeeModel, token);
    }
}
