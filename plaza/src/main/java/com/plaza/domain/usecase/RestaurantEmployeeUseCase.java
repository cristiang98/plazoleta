package com.plaza.domain.usecase;

import com.plaza.domain.api.IRestaurantEmployeeServicePort;
import com.plaza.domain.model.RestaurantEmployeeModel;
import com.plaza.domain.model.RestaurantModel;
import com.plaza.domain.spi.IJwtHanlderPort;
import com.plaza.domain.spi.IRestaurantEmployeePersistencePort;
import com.plaza.domain.spi.IRestaurantPersistencePort;

public class RestaurantEmployeeUseCase implements IRestaurantEmployeeServicePort {

    private final IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IJwtHanlderPort jwtHanlderPort;

    public RestaurantEmployeeUseCase(IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort, IRestaurantPersistencePort restaurantPersistencePort, IJwtHanlderPort jwtHanlderPort) {
        this.restaurantEmployeePersistencePort = restaurantEmployeePersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.jwtHanlderPort = jwtHanlderPort;
    }

    @Override
    public void saveEmployeeRestaurant(RestaurantEmployeeModel restaurantEmployeeModel, String token) {
        Integer ownerDni = jwtHanlderPort.extractDni(token);
        RestaurantModel restaurantModel = restaurantPersistencePort.findByIdOwner(ownerDni);
        restaurantEmployeeModel.setRestaurantNit(restaurantModel.getNit());
        restaurantEmployeePersistencePort.saveEmployeeRestaurant(restaurantEmployeeModel);
    }
}
