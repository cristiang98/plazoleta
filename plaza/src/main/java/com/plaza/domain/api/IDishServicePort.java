package com.plaza.domain.api;

import com.plaza.domain.model.DishModel;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IDishServicePort {

    DishModel saveDish(DishModel dishModel ,String token);

    DishModel findById(Integer id);

    void updateDish(Integer id,DishModel dishModel, String token);

    void toggleDish(Integer id, String token);

    Page<DishModel> getDishes(Integer nit ,int page, int size, String categoryFilter);
}
