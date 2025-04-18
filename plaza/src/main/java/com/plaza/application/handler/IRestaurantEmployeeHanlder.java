package com.plaza.application.handler;

import com.plaza.application.dto.request.RestaurantEmpRequestDto;

public interface IRestaurantEmployeeHanlder {

    void saveEmployeeRestaurant(RestaurantEmpRequestDto restaurantEmpRequestDto, String token);

}
