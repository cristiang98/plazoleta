package com.plaza.domain.spi;

import com.plaza.domain.model.RestaurantEmployeeModel;

public interface IRestaurantEmployeePersistencePort {

    void saveEmployeeRestaurant(RestaurantEmployeeModel restaurantEmployeeModel);

    RestaurantEmployeeModel findEmployeeRestaurant(Integer id);

}
