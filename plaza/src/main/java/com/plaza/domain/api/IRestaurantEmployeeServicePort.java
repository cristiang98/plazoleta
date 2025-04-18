package com.plaza.domain.api;

import com.plaza.domain.model.RestaurantEmployeeModel;

public interface IRestaurantEmployeeServicePort {

    void saveEmployeeRestaurant(RestaurantEmployeeModel restaurantEmployeeModel, String token);

}
