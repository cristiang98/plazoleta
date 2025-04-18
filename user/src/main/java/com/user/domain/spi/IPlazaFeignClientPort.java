package com.user.domain.spi;

import com.user.domain.dto.RestaurantEmpRequestDto;


public interface IPlazaFeignClientPort {

    void saveRestaurantEmployee(RestaurantEmpRequestDto restaurantEmpRequestDto);

}
