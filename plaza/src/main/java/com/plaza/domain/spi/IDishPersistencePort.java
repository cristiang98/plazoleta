package com.plaza.domain.spi;

import com.plaza.domain.model.DishModel;
import org.springframework.data.domain.Page;

public interface IDishPersistencePort {

    DishModel saveDish(DishModel dishModel);

    DishModel findById(Integer id);

    void updateDish(DishModel dishModel);

    Page<DishModel> getDishes(Integer nit ,int page, int size, String categoryFilter);
}
