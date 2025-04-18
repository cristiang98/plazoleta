package com.plaza.application.handler.impl;

import com.plaza.application.dto.request.RestaurantRequestDto;
import com.plaza.application.exception.RestaurantExistException;
import com.plaza.application.mapper.IRestaurantRequestMapper;
import com.plaza.domain.api.IRestaurantServicePort;
import com.plaza.domain.model.RestaurantModel;
import com.plaza.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RestaurantHandlerImpTest {

    @Mock
    private IRestaurantServicePort restaurantServicePort;

    @Mock
    private IRestaurantRequestMapper restaurantRequestMapper;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @InjectMocks
    private RestaurantHandlerImp restaurantHandlerImp;

    public RestaurantHandlerImpTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveRestaurant_ShouldThrowException_WhenRestaurantExists() {
        RestaurantRequestDto dto = new RestaurantRequestDto(12345, "My Restaurant", "123 Main St", "+123456789", "http://logo.url", 1);

        when(restaurantPersistencePort.existsRestaurant(dto.getNit())).thenReturn(true);

        assertThrows(RestaurantExistException.class, () -> restaurantHandlerImp.saveRestaurant(dto));

        verify(restaurantPersistencePort, times(1)).existsRestaurant(dto.getNit());
        verifyNoInteractions(restaurantRequestMapper);
        verifyNoInteractions(restaurantServicePort);
    }

    @Test
    void saveRestaurant_ShouldSaveRestaurant_WhenRestaurantDoesNotExist() {
        RestaurantRequestDto dto = new RestaurantRequestDto(12345, "Valid Name", "123 Main St", "+123456789", "http://logo.url", 1);
        RestaurantModel restaurantModel = new RestaurantModel();

        when(restaurantPersistencePort.existsRestaurant(dto.getNit())).thenReturn(false);
        when(restaurantRequestMapper.toRestaurantModel(dto)).thenReturn(restaurantModel);

        assertDoesNotThrow(() -> restaurantHandlerImp.saveRestaurant(dto));

        verify(restaurantPersistencePort, times(1)).existsRestaurant(dto.getNit());
        verify(restaurantRequestMapper, times(1)).toRestaurantModel(dto);
        verify(restaurantServicePort, times(1)).saveRestaurant(restaurantModel);
    }
}