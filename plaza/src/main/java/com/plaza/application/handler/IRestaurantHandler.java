package com.plaza.application.handler;

import com.plaza.application.dto.request.RestaurantRequestDto;
import com.plaza.application.dto.response.RestaurantResponseDto;
import org.springframework.data.domain.Page;

public interface IRestaurantHandler {

    void saveRestaurant(RestaurantRequestDto restaurantRequestDto);

    Page<RestaurantResponseDto> getRestaurants(int page, int size);
}
