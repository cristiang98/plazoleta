package com.plaza.application.handler.impl;

import com.plaza.application.dto.request.RestaurantEmpRequestDto;
import com.plaza.application.mapper.IRestaurantEmplRequestMapper;
import com.plaza.domain.api.IRestaurantEmployeeServicePort;
import com.plaza.domain.model.RestaurantEmployeeModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class RestaurantEmployeeHandlerTest {

    @Mock
    private IRestaurantEmployeeServicePort restaurantEmployeeServicePort;

    @Mock
    private IRestaurantEmplRequestMapper restaurantEmplRequestMapper;

    @InjectMocks
    private RestaurantEmployeeHandler restaurantEmployeeHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveEmployeeRestaurant_ShouldInvokeServicePortSave_WhenValidRequestIsProvided() {
        // Arrange
        RestaurantEmpRequestDto requestDto = RestaurantEmpRequestDto.builder()
                .employeeId(1)
                .build();
        String token = "validToken";

        RestaurantEmployeeModel expectedModel = new RestaurantEmployeeModel();
        when(restaurantEmplRequestMapper.toModel(requestDto)).thenReturn(expectedModel);

        // Act & Assert
        assertDoesNotThrow(() -> restaurantEmployeeHandler.saveEmployeeRestaurant(requestDto, token));

        verify(restaurantEmplRequestMapper, times(1)).toModel(requestDto);
        verify(restaurantEmployeeServicePort, times(1)).saveEmployeeRestaurant(eq(expectedModel), eq(token));
    }
}