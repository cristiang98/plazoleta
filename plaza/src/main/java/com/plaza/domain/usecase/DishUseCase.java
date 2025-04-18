package com.plaza.domain.usecase;

import com.plaza.domain.exception.DishNotFoundException;
import com.plaza.domain.api.IDishServicePort;
import com.plaza.domain.exception.NitNotNullException;
import com.plaza.domain.exception.UserWithoutPermission;
import com.plaza.domain.model.DishModel;
import com.plaza.domain.spi.IDishPersistencePort;
import com.plaza.domain.spi.IJwtHanlderPort;
import com.plaza.domain.spi.IRestaurantPersistencePort;
import org.springframework.data.domain.Page;

public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IJwtHanlderPort jwtHandler;

    public DishUseCase(IDishPersistencePort dishPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, IJwtHanlderPort jwtHandler) {
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.jwtHandler = jwtHandler;
    }


    @Override
    public DishModel saveDish(DishModel dishModel, String token) {

        int dniToken = jwtHandler.extractDni(token);

        if (dniToken != dishModel.getRestaurant().getIdOwner()) {
            throw new UserWithoutPermission("No tienes permisos para realizar esta acción saveDish");
        }
        dishModel.setStatus(true);
        return dishPersistencePort.saveDish(dishModel);
    }

    @Override
    public DishModel findById(Integer id) {

        DishModel dishModel = dishPersistencePort.findById(id);
        if (dishModel == null) {
            throw new DishNotFoundException("El platillo con el id " + id + " no existe");
        }
        return dishModel;
    }

    @Override
    public void updateDish(Integer id ,DishModel modidyDishModel, String token) {

        Integer dniToken = jwtHandler.extractDni(token);
        DishModel existDish = dishPersistencePort.findById(id);

        if (existDish == null) {
            throw new DishNotFoundException("El platillo con id " + id + " no existe");
        }

        if (!existDish.getRestaurant().getIdOwner().equals(dniToken)) {
            throw new UserWithoutPermission("No tienes permisos para realizar esta acción updateDish");
        }

        existDish.setDescription(modidyDishModel.getDescription());
        existDish.setPrice(modidyDishModel.getPrice());

        dishPersistencePort.updateDish(existDish);
    }

    @Override
    public void toggleDish(Integer id, String token) {

        Integer dniToken = jwtHandler.extractDni(token);
        DishModel existDish = dishPersistencePort.findById(id);

        if (existDish == null) {
            throw new DishNotFoundException("El platillo con id " + id + " no se encuentra");
        }

        if (!existDish.getRestaurant().getIdOwner().equals(dniToken)) {
            throw new UserWithoutPermission("No tienes permisos para realizar esta acción toggleDish");
        }

        existDish.setStatus(!existDish.getStatus());
        dishPersistencePort.updateDish(existDish);
    }

    @Override
    public Page<DishModel> getDishes(Integer nit ,int page, int size, String categoryFilter) {

        if(nit == null){
            throw new NitNotNullException("El nit no puede ser nulo");
        }

        return dishPersistencePort.getDishes(nit ,page, size, categoryFilter);
    }

}
