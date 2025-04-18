package com.plaza.domain.usecase;

import com.plaza.domain.dto.RoleResponseDto;
import com.plaza.domain.dto.UserResponseDto;
import com.plaza.domain.exception.NotOwnerException;
import com.plaza.domain.model.RestaurantModel;
import com.plaza.domain.spi.IRestaurantPersistencePort;
import com.plaza.domain.spi.IUserFeignClientPort;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantUseCaseTest {


    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IUserFeignClientPort userFeignClientPort;

    @InjectMocks
    private RestaurantUseCase restaurantUseCase;

    public RestaurantUseCaseTest() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveRestaurantWhenUserIsOwner() {
        // Arrange
        RestaurantModel restaurantModel = new RestaurantModel(123, "Test Restaurant", "123 Street", 999999999, "http://logo.com", 1);
        UserResponseDto userResponseDto = new UserResponseDto();
        RoleResponseDto roleResponseDto = new RoleResponseDto( 2,"PROPIETARIO", "effefef" );
        userResponseDto.setRole(roleResponseDto);

        when(userFeignClientPort.findByDni(restaurantModel.getIdOwner())).thenReturn(userResponseDto);
        when(restaurantPersistencePort.saveRestaurant(restaurantModel)).thenReturn(restaurantModel);

        // Act
        RestaurantModel result = restaurantUseCase.saveRestaurant(restaurantModel);

        // Assert
        assertNotNull(result);
        assertEquals(restaurantModel, result);
        verify(userFeignClientPort, times(1)).findByDni(restaurantModel.getIdOwner());
        verify(restaurantPersistencePort, times(1)).saveRestaurant(restaurantModel);
    }

    @Test
    void shouldThrowNotOwnerExceptionWhenUserIsNotOwner() {
        // Arrange
        RestaurantModel restaurantModel = new RestaurantModel(123, "Test Restaurant", "123 Street", 999999999, "http://logo.com", 1);
        UserResponseDto userResponseDto = new UserResponseDto();
        RoleResponseDto roleResponseDto = new RoleResponseDto( 3,"CLIENTE", "EFEFEFEFEF" );
        userResponseDto.setRole(roleResponseDto);

        when(userFeignClientPort.findByDni(restaurantModel.getIdOwner())).thenReturn(userResponseDto);

        // Act & Assert
        NotOwnerException exception = assertThrows(NotOwnerException.class, () -> restaurantUseCase.saveRestaurant(restaurantModel));

        assertEquals("No eres propietario", exception.getMessage());
        verify(userFeignClientPort, times(1)).findByDni(restaurantModel.getIdOwner());
        verify(restaurantPersistencePort, never()).saveRestaurant(restaurantModel);
    }
    @Test
    void shouldReturnRestaurantWhenFoundByName() {
        // Arrange
        String restaurantName = "Test Restaurant";
        RestaurantModel restaurantModel = new RestaurantModel(123, restaurantName, "123 Street", 999999999, "http://logo.com", 1);
        when(restaurantPersistencePort.findByName(restaurantName)).thenReturn(restaurantModel);

        // Act
        RestaurantModel result = restaurantUseCase.findByName(restaurantName);

        // Assert
        assertNotNull(result);
        assertEquals(restaurantName, result.getName());
        verify(restaurantPersistencePort, times(1)).findByName(restaurantName);
    }

    @Test
    void shouldReturnNullWhenRestaurantNotFoundByName() {
        // Arrange
        String restaurantName = "Non-Existent Restaurant";
        when(restaurantPersistencePort.findByName(restaurantName)).thenReturn(null);

        // Act
        RestaurantModel result = restaurantUseCase.findByName(restaurantName);

        // Assert
        assertNull(result);
        verify(restaurantPersistencePort, times(1)).findByName(restaurantName);
    }

    @Test
    void shouldReturnPagedRestaurantList() {
        // Arrange
        int page = 0;
        int size = 2;
        RestaurantModel restaurant1 = new RestaurantModel(123, "Restaurant 1", "Address 1", 123456789, "http://logo1.com", 1);
        RestaurantModel restaurant2 = new RestaurantModel(124, "Restaurant 2", "Address 2", 987654321, "http://logo2.com", 2);
        Page<RestaurantModel> restaurantPage = mock(Page.class);
        when(restaurantPage.getContent()).thenReturn(List.of(restaurant1, restaurant2));
        when(restaurantPersistencePort.findAll(page, size)).thenReturn(restaurantPage);

        // Act
        Page<RestaurantModel> result = restaurantUseCase.getRestaurants(page, size);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("Restaurant 1", result.getContent().get(0).getName());
        assertEquals("Restaurant 2", result.getContent().get(1).getName());
        verify(restaurantPersistencePort, times(1)).findAll(page, size);
    }

}
