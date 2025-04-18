package com.user.infrastructure.out.jpa.adapter;

import com.user.domain.dto.RestaurantEmpRequestDto;
import com.user.domain.spi.IPlazaFeignClientPort;
import com.user.infrastructure.feign.IPlazaFeignClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlazaFeignAdapter implements IPlazaFeignClientPort {

    private final IPlazaFeignClient plazaFeignClient;

    @Override
    public void saveRestaurantEmployee(RestaurantEmpRequestDto restaurantEmpRequestDto) {
        plazaFeignClient.saveRestaurantEmployee(restaurantEmpRequestDto);
    }
}
