package com.plaza.domain.usecase;

import com.plaza.domain.api.IRestaurantServicePort;
import com.plaza.domain.dto.UserResponseDto;
import com.plaza.domain.exception.NotOwnerException;
import com.plaza.domain.model.RestaurantModel;
import com.plaza.domain.spi.IRestaurantPersistencePort;
import com.plaza.domain.spi.IUserFeignClientPort;
import org.springframework.data.domain.Page;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserFeignClientPort userFeignClientPort;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IUserFeignClientPort userFeignClientPort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userFeignClientPort = userFeignClientPort;
    }

    @Override
    public RestaurantModel saveRestaurant(RestaurantModel restaurantModel) {
        UserResponseDto userResponseDto = userFeignClientPort.findByDni(restaurantModel.getIdOwner());
        if (!userResponseDto.getRole().getName().equals("PROPIETARIO")) {
            throw new NotOwnerException("No eres propietario");
        }
        return restaurantPersistencePort.saveRestaurant(restaurantModel);
    }

    @Override
    public RestaurantModel findByName(String name) {
        return restaurantPersistencePort.findByName(name);
    }

    @Override
    public RestaurantModel findById(Integer id) {
        return restaurantPersistencePort.findById(id);
    }

    @Override
    public Page<RestaurantModel> getRestaurants(int page, int size) {
        return restaurantPersistencePort.findAll(page, size);
    }

}
