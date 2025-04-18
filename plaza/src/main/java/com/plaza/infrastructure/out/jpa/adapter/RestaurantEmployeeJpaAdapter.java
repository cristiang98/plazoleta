package com.plaza.infrastructure.out.jpa.adapter;

import com.plaza.domain.model.RestaurantEmployeeModel;
import com.plaza.domain.spi.IRestaurantEmployeePersistencePort;
import com.plaza.infrastructure.out.jpa.entity.RestaurantEmployeeEntity;
import com.plaza.infrastructure.out.jpa.mapper.IRestaurantEmplEntityMapper;
import com.plaza.infrastructure.out.jpa.repository.IRestaurantEmployeeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantEmployeeJpaAdapter implements IRestaurantEmployeePersistencePort {

    private final IRestaurantEmployeeRepository restaurantEmployeeRepository;
    private final IRestaurantEmplEntityMapper restaurantEmplEntityMapper;


    @Override
    public void saveEmployeeRestaurant(RestaurantEmployeeModel restaurantEmployeeModel) {
        RestaurantEmployeeEntity restaurantEntity = restaurantEmplEntityMapper.toEntity(restaurantEmployeeModel);
        restaurantEmployeeRepository.save(restaurantEntity);
    }

    @Override
    public RestaurantEmployeeModel findEmployeeRestaurant(Integer id) {
        RestaurantEmployeeEntity restaurantEntity = restaurantEmployeeRepository.findByEmployeeId(id);
        return restaurantEmplEntityMapper.toModel(restaurantEntity);
    }
}
