package com.plaza.infrastructure.out.jpa.adapter;

import com.plaza.domain.model.RestaurantEmployeeModel;
import com.plaza.infrastructure.out.jpa.entity.RestaurantEmployeeEntity;
import com.plaza.infrastructure.out.jpa.mapper.IRestaurantEmplEntityMapper;
import com.plaza.infrastructure.out.jpa.repository.IRestaurantEmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantEmployeeJpaAdapterTest {

    @Mock
    private IRestaurantEmployeeRepository restaurantEmployeeRepository;

    @Mock
    private IRestaurantEmplEntityMapper restaurantEmplEntityMapper;

    @InjectMocks
    private RestaurantEmployeeJpaAdapter restaurantEmployeeJpaAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveEmployeeRestaurant_validModel_savesEntity() {
        // Arrange
        RestaurantEmployeeModel model = new RestaurantEmployeeModel(1234, 5678);
        RestaurantEmployeeEntity entity = new RestaurantEmployeeEntity();
        entity.setRestaurantNit(1234);
        entity.setEmployeeId(5678);

        when(restaurantEmplEntityMapper.toEntity(model)).thenReturn(entity);

        // Act
        restaurantEmployeeJpaAdapter.saveEmployeeRestaurant(model);

        // Assert
        ArgumentCaptor<RestaurantEmployeeEntity> entityCaptor = ArgumentCaptor.forClass(RestaurantEmployeeEntity.class);
        verify(restaurantEmployeeRepository, times(1)).save(entityCaptor.capture());
        RestaurantEmployeeEntity capturedEntity = entityCaptor.getValue();

        assertNotNull(capturedEntity);
        assertEquals(1234, capturedEntity.getRestaurantNit());
        assertEquals(5678, capturedEntity.getEmployeeId());
    }


}