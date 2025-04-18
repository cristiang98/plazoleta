package com.plaza.application.handler.impl;

import com.plaza.application.dto.request.DishRequestDto;
import com.plaza.application.dto.request.UpdateDishRequestDto;
import com.plaza.application.mapper.IDishRequestMapper;
import com.plaza.application.mapper.response.IDishResponseMapper;
import com.plaza.domain.api.ICategoryServicePort;
import com.plaza.domain.api.IDishServicePort;
import com.plaza.domain.api.IRestaurantServicePort;
import com.plaza.domain.model.CategoryModel;
import com.plaza.domain.model.DishModel;
import com.plaza.domain.model.RestaurantModel;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

public class DishHandlerImpTest {

    @Mock
    private IDishServicePort dishServicePort;

    @Mock
    private IDishRequestMapper dishRequestMapper;

    @Mock
    private IDishResponseMapper dishResponseMapper;

    @Mock
    private ICategoryServicePort categoryServicePort;

    @Mock
    private IRestaurantServicePort restaurantServicePort;

    @InjectMocks
    private DishHandlerImp dishHandlerImp;

    public DishHandlerImpTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveDish_ShouldSaveDishSuccessfully() {
        // Arrange
        DishRequestDto requestDto = new DishRequestDto("DishName", 100, "Description", "http://image.url", "CategoryName", "RestaurantName");

        DishModel dishModel = new DishModel();
        when(dishRequestMapper.toDishModel(requestDto)).thenReturn(dishModel);

        CategoryModel categoryModel = new CategoryModel();
        when(categoryServicePort.findByName(requestDto.getCategoryName())).thenReturn(categoryModel);

        RestaurantModel restaurantModel = new RestaurantModel();
        when(restaurantServicePort.findByName(requestDto.getRestaurantName())).thenReturn(restaurantModel);

        // Act
        dishHandlerImp.saveDish(requestDto, "token");

        // Assert
        verify(dishRequestMapper, times(1)).toDishModel(requestDto);
        verify(categoryServicePort, times(1)).findByName(requestDto.getCategoryName());
        verify(restaurantServicePort, times(1)).findByName(requestDto.getRestaurantName());
        verify(dishServicePort, times(1)).saveDish(dishModel, "token");
        verifyNoMoreInteractions(dishRequestMapper, categoryServicePort, restaurantServicePort, dishServicePort);
    }

    @Test
    void updateDish_ShouldUpdateDishSuccessfully() {
        // Arrange
        UpdateDishRequestDto updateDishRequestDto = new UpdateDishRequestDto("Updated Description", 150);
        DishModel dishModel = new DishModel();
        when(dishRequestMapper.toDishModel(updateDishRequestDto)).thenReturn(dishModel);

        // Act
        dishHandlerImp.updateDish(1, updateDishRequestDto, "token");

        // Assert
        verify(dishRequestMapper, times(1)).toDishModel(updateDishRequestDto);
        verify(dishServicePort, times(1)).updateDish(1, dishModel, "token");
    }


    @Test
    void toggleDish_ShouldToggleDishSuccessfully() {
        // Arrange
        Integer dishId = 1;
        String token = "token";

        // Act
        dishHandlerImp.toggleDish(dishId, token);

        // Assert
        verify(dishServicePort, times(1)).toggleDish(dishId, token);
        verifyNoMoreInteractions(dishServicePort);
    }
}