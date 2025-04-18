package com.plaza.application.handler.impl;

import com.plaza.application.dto.request.DishRequestDto;
import com.plaza.application.dto.request.UpdateDishRequestDto;
import com.plaza.application.dto.response.DishResponseDto;
import com.plaza.application.handler.IDishHandler;
import com.plaza.application.mapper.IDishRequestMapper;
import com.plaza.application.mapper.response.IDishResponseMapper;
import com.plaza.domain.api.ICategoryServicePort;
import com.plaza.domain.api.IDishServicePort;
import com.plaza.domain.api.IRestaurantServicePort;
import com.plaza.domain.model.CategoryModel;
import com.plaza.domain.model.DishModel;
import com.plaza.domain.model.RestaurantModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DishHandlerImp implements IDishHandler {

    private final IDishServicePort dishServicePort;
    @Qualifier("IDishRequestMapper")
    private final IDishRequestMapper dishRequestMapper;
    private final ICategoryServicePort categoryServicePort;
    private final IRestaurantServicePort restaurantServicePort;
    private final IDishResponseMapper dishResponseMapper;

    @Override
    public void saveDish(DishRequestDto dishRequestDto, String token) {

        DishModel dishModel = dishRequestMapper.toDishModel(dishRequestDto);
        CategoryModel categoryModel = categoryServicePort.findByName(dishRequestDto.getCategoryName());
        RestaurantModel restaurantModel = restaurantServicePort.findByName(dishRequestDto.getRestaurantName());
        dishModel.setCategory(categoryModel);
        dishModel.setRestaurant(restaurantModel);
        dishServicePort.saveDish(dishModel, token);
    }

    @Override
    public void updateDish(Integer id, UpdateDishRequestDto updateDishRequestDto, String token) {

        DishModel dishModel = dishRequestMapper.toDishModel(updateDishRequestDto);

        dishServicePort.updateDish(id,dishModel, token);
    }

    @Override
    public void toggleDish(Integer id, String token) {
        dishServicePort.toggleDish(id, token);
    }

    @Override
    public Page<DishResponseDto> getDishes(Integer nit ,int page, int size, String categoryFilter) {
        return dishServicePort.getDishes(nit,page, size, categoryFilter).map(dishResponseMapper::toDishRequestDto);
    }

}
