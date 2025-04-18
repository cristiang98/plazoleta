package com.plaza.domain.spi;

import com.plaza.domain.model.RestaurantModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRestaurantPersistencePort {

    RestaurantModel saveRestaurant(RestaurantModel restaurantModel);
    Boolean existsRestaurant(Integer nit);
    RestaurantModel findByIdOwner(Integer idOwner);
    RestaurantModel findByName(String name);
    RestaurantModel findById(Integer id);
    Page<RestaurantModel> findAll(int page, int size);
}
