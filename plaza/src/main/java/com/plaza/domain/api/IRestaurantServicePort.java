package com.plaza.domain.api;

import com.plaza.domain.model.RestaurantModel;
import org.springframework.data.domain.Page;

public interface IRestaurantServicePort {

    RestaurantModel saveRestaurant(RestaurantModel restaurantModel);
    RestaurantModel findByName(String name);
    RestaurantModel findById(Integer id);

    Page<RestaurantModel> getRestaurants(int page, int size);
}
