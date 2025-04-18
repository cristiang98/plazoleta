package com.plaza.domain.usecase;

import com.plaza.domain.exception.DishNotFoundException;
import com.plaza.domain.exception.NitNotNullException;
import com.plaza.domain.exception.UserWithoutPermission;
import com.plaza.domain.model.DishModel;
import com.plaza.domain.model.RestaurantModel;
import com.plaza.domain.spi.IDishPersistencePort;
import com.plaza.domain.spi.IJwtHanlderPort;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DishUseCaseTest {

    @Mock
    private IDishPersistencePort dishPersistencePort;

    @Mock
    private IJwtHanlderPort jwtHandler;

    @InjectMocks
    private DishUseCase dishUseCase;

    public DishUseCaseTest() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void shouldSaveDishWhenUserHasPermission() {
        // Arrange
        String validToken = "valid_token";
        int ownerDni = 12345;

        RestaurantModel restaurant = new RestaurantModel();
        restaurant.setIdOwner(ownerDni);

        DishModel dishModel = new DishModel();
        dishModel.setRestaurant(restaurant);
        dishModel.setStatus(false);

        when(jwtHandler.extractDni(validToken)).thenReturn(ownerDni);
        when(dishPersistencePort.saveDish(dishModel)).thenReturn(dishModel);

        // Act
        DishModel result = dishUseCase.saveDish(dishModel, validToken);

        // Assert
        assertEquals(dishModel, result);
        assertEquals(true, result.getStatus());
        verify(jwtHandler, times(1)).extractDni(validToken);
        verify(dishPersistencePort, times(1)).saveDish(dishModel);
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotHavePermission() {
        // Arrange
        String invalidToken = "invalid_token";
        int ownerDni = 12345;
        int invalidDni = 98765;

        RestaurantModel restaurant = new RestaurantModel();
        restaurant.setIdOwner(ownerDni);

        DishModel dishModel = new DishModel();
        dishModel.setRestaurant(restaurant);

        when(jwtHandler.extractDni(invalidToken)).thenReturn(invalidDni);

        // Act & Assert
        assertThrows(UserWithoutPermission.class, () -> dishUseCase.saveDish(dishModel, invalidToken));
        verify(jwtHandler, times(1)).extractDni(invalidToken);
        verify(dishPersistencePort, never()).saveDish(any());
    }

    @Test
    void shouldReturnDishWhenFoundById() {
        // Arrange
        Integer dishId = 1;
        DishModel expectedDish = new DishModel();
        expectedDish.setId(dishId);

        when(dishPersistencePort.findById(dishId)).thenReturn(expectedDish);

        // Act
        DishModel result = dishUseCase.findById(dishId);

        // Assert
        assertEquals(expectedDish, result);
        verify(dishPersistencePort, times(1)).findById(dishId);
    }

    @Test
    void shouldThrowExceptionWhenDishNotFound() {
        // Arrange
        Integer dishId = 1;
        when(dishPersistencePort.findById(dishId)).thenReturn(null);

        // Act & Assert
        assertThrows(DishNotFoundException.class, () -> dishUseCase.findById(dishId));
        verify(dishPersistencePort, times(1)).findById(dishId);
    }

    @Test
    void shouldUpdateDishWhenUserHasPermission() {
        // Arrange
        String validToken = "valid_token";
        Integer ownerDni = 12345;
        Integer dishId = 1;

        RestaurantModel restaurant = new RestaurantModel();
        restaurant.setIdOwner(ownerDni);

        DishModel existingDish = new DishModel();
        existingDish.setId(dishId);
        existingDish.setRestaurant(restaurant);

        DishModel modifiedDish = new DishModel("Updated description", 200);

        when(jwtHandler.extractDni(validToken)).thenReturn(ownerDni);
        when(dishPersistencePort.findById(dishId)).thenReturn(existingDish);

        // Act
        dishUseCase.updateDish(dishId, modifiedDish, validToken);

        // Assert
        verify(jwtHandler, times(1)).extractDni(validToken);
        verify(dishPersistencePort, times(1)).findById(dishId);
        verify(dishPersistencePort, times(1)).updateDish(existingDish);
        assertEquals(modifiedDish.getDescription(), existingDish.getDescription());
        assertEquals(modifiedDish.getPrice(), existingDish.getPrice());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingDishAndUserHasNoPermission() {
        // Arrange
        String token = "invalid_token";
        Integer ownerDni = 12345;
        Integer invalidDni = 98765;
        Integer dishId = 1;

        RestaurantModel restaurant = new RestaurantModel();
        restaurant.setIdOwner(ownerDni);

        DishModel existingDish = new DishModel();
        existingDish.setId(dishId);
        existingDish.setRestaurant(restaurant);

        DishModel modifiedDish = new DishModel("Updated description", 200);

        when(jwtHandler.extractDni(token)).thenReturn(invalidDni);
        when(dishPersistencePort.findById(dishId)).thenReturn(existingDish);

        // Act & Assert
        assertThrows(UserWithoutPermission.class, () -> dishUseCase.updateDish(dishId, modifiedDish, token));
        verify(jwtHandler, times(1)).extractDni(token);
        verify(dishPersistencePort, times(1)).findById(dishId);
        verify(dishPersistencePort, never()).updateDish(existingDish);
    }

    @Test
    void shouldThrowExceptionWhenDishToUpdateNotFound() {
        // Arrange
        String token = "valid_token";
        Integer dishId = 1;
        Integer ownerDni = 12345;

        DishModel modifiedDish = new DishModel("Updated description", 200);

        when(jwtHandler.extractDni(token)).thenReturn(ownerDni);
        when(dishPersistencePort.findById(dishId)).thenReturn(null);

        // Act & Assert
        assertThrows(DishNotFoundException.class, () -> dishUseCase.updateDish(dishId, modifiedDish, token));
        verify(jwtHandler, times(1)).extractDni(token);
        verify(dishPersistencePort, times(1)).findById(dishId);
        verify(dishPersistencePort, never()).updateDish(any());
    }

    @Test
    void shouldToggleDishStatusWhenUserHasPermission() {
        // Arrange
        String validToken = "valid_token";
        Integer ownerDni = 12345;
        Integer dishId = 1;

        RestaurantModel restaurant = new RestaurantModel();
        restaurant.setIdOwner(ownerDni);

        DishModel dish = new DishModel();
        dish.setId(dishId);
        dish.setRestaurant(restaurant);
        dish.setStatus(false); // Initial status

        when(jwtHandler.extractDni(validToken)).thenReturn(ownerDni);
        when(dishPersistencePort.findById(dishId)).thenReturn(dish);

        // Act
        dishUseCase.toggleDish(dishId, validToken);

        // Assert
        verify(jwtHandler, times(1)).extractDni(validToken);
        verify(dishPersistencePort, times(1)).findById(dishId);
        verify(dishPersistencePort, times(1)).updateDish(dish);
        assertEquals(true, dish.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenDishToToggleNotFound() {
        // Arrange
        String validToken = "valid_token";
        Integer dishId = 1;

        when(jwtHandler.extractDni(validToken)).thenReturn(12345);
        when(dishPersistencePort.findById(dishId)).thenReturn(null);

        // Act & Assert
        assertThrows(DishNotFoundException.class, () -> dishUseCase.toggleDish(dishId, validToken));
        verify(jwtHandler, times(1)).extractDni(validToken);
        verify(dishPersistencePort, times(1)).findById(dishId);
        verify(dishPersistencePort, never()).updateDish(any());
    }

    @Test
    void shouldThrowExceptionWhenTogglingDishAndUserHasNoPermission() {
        // Arrange
        String invalidToken = "invalid_token";
        Integer dishId = 1;
        Integer ownerDni = 12345;
        Integer invalidDni = 98765;

        RestaurantModel restaurant = new RestaurantModel();
        restaurant.setIdOwner(ownerDni);

        DishModel dish = new DishModel();
        dish.setId(dishId);
        dish.setRestaurant(restaurant);
        dish.setStatus(false); // Initial status

        when(jwtHandler.extractDni(invalidToken)).thenReturn(invalidDni);
        when(dishPersistencePort.findById(dishId)).thenReturn(dish);

        // Act & Assert
        assertThrows(UserWithoutPermission.class, () -> dishUseCase.toggleDish(dishId, invalidToken));
        verify(jwtHandler, times(1)).extractDni(invalidToken);
        verify(dishPersistencePort, times(1)).findById(dishId);
        verify(dishPersistencePort, never()).updateDish(any());
    }

    @Test
    void shouldReturnDishesWhenValidNitAndFiltersAreProvided() {
        // Arrange
        Integer nit = 12345;
        int page = 0;
        int size = 5;
        String categoryFilter = "Category";

        Page<DishModel> mockPage = mock(Page.class);
        when(dishPersistencePort.getDishes(nit, page, size, categoryFilter)).thenReturn(mockPage);

        // Act
        Page<DishModel> result = dishUseCase.getDishes(nit, page, size, categoryFilter);

        // Assert
        assertEquals(mockPage, result);
        verify(dishPersistencePort, times(1)).getDishes(nit, page, size, categoryFilter);
    }

    @Test
    void shouldThrowExceptionWhenNitIsNull() {
        // Arrange
        Integer nit = null;
        int page = 0;
        int size = 5;
        String categoryFilter = "Category";

        // Act & Assert
        assertThrows(NitNotNullException.class, () -> dishUseCase.getDishes(nit, page, size, categoryFilter));
        verify(dishPersistencePort, never()).getDishes(any(), anyInt(), anyInt(), any());
    }
}