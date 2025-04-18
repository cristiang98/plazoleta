package com.user.infrastructure.feign;

import com.user.domain.dto.RestaurantEmpRequestDto;
import com.user.infrastructure.feign.interceptor.FeignClientInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "plazas", path = "/api/v1/restaurant-employee", configuration = FeignClientInterceptor.class)
public interface IPlazaFeignClient {
    @PostMapping("/save")
    void saveRestaurantEmployee(RestaurantEmpRequestDto restaurantEmpRequestDto);

}
