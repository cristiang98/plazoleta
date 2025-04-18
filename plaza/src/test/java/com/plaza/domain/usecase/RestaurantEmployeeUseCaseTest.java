package com.plaza.domain.usecase;

import com.plaza.domain.model.RestaurantEmployeeModel;
import com.plaza.domain.model.RestaurantModel;
import com.plaza.domain.spi.IJwtHanlderPort;
import com.plaza.domain.spi.IRestaurantEmployeePersistencePort;
import com.plaza.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RestaurantEmployeeUseCaseTest {


    @Mock
    private IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IJwtHanlderPort jwtHandlerPort;

    @InjectMocks
    private RestaurantEmployeeUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveEmployeeRestaurant_Success() {
        // Arrange
        String token = "mock-token";
        Integer ownerDni = 12345678;
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setNit(1234);

        RestaurantEmployeeModel employeeModel = new RestaurantEmployeeModel();
        employeeModel.setEmployeeId(5678);

        when(jwtHandlerPort.extractDni(token)).thenReturn(ownerDni);
        when(restaurantPersistencePort.findByIdOwner(ownerDni)).thenReturn(restaurantModel);

        // Act
        useCase.saveEmployeeRestaurant(employeeModel, token);

        // Assert
        assertEquals(1234, employeeModel.getRestaurantNit());
        verify(jwtHandlerPort, times(1)).extractDni(token);
        verify(restaurantPersistencePort, times(1)).findByIdOwner(ownerDni);
        verify(restaurantEmployeePersistencePort, times(1)).saveEmployeeRestaurant(eq(employeeModel));
    }

    @Test
    void testSaveEmployeeRestaurant_ThrowsException_WhenOwnerDniIsInvalid() {
        // Arrange
        String token = "invalid-token";

        when(jwtHandlerPort.extractDni(token)).thenThrow(new RuntimeException("Invalid token"));

        RestaurantEmployeeModel employeeModel = new RestaurantEmployeeModel();
        employeeModel.setEmployeeId(5678);

        // Act & Assert
        try {
            useCase.saveEmployeeRestaurant(employeeModel, token);
        } catch (RuntimeException e) {
            assertEquals("Invalid token", e.getMessage());
        }

        verify(jwtHandlerPort, times(1)).extractDni(token);
        verify(restaurantPersistencePort, never()).findByIdOwner(any());
        verify(restaurantEmployeePersistencePort, never()).saveEmployeeRestaurant(any());
    }

    @Test
    void testSaveEmployeeRestaurant_ThrowsException_WhenRestaurantNotFound() {
        // Arrange
        IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort = Mockito.mock(IRestaurantEmployeePersistencePort.class);
        IRestaurantPersistencePort restaurantPersistencePort = Mockito.mock(IRestaurantPersistencePort.class);
        IJwtHanlderPort jwtHandlerPort = mock(IJwtHanlderPort.class);

        RestaurantEmployeeUseCase useCase = new RestaurantEmployeeUseCase(
                restaurantEmployeePersistencePort,
                restaurantPersistencePort,
                jwtHandlerPort
        );

        String token = "mock-token";
        Integer ownerDni = 12345678;

        RestaurantEmployeeModel employeeModel = new RestaurantEmployeeModel();
        employeeModel.setEmployeeId(5678);

        when(jwtHandlerPort.extractDni(token)).thenReturn(ownerDni);
        when(restaurantPersistencePort.findByIdOwner(ownerDni)).thenThrow(new RuntimeException("Restaurant not found"));

        // Act & Assert
        try {
            useCase.saveEmployeeRestaurant(employeeModel, token);
        } catch (RuntimeException e) {
            assertEquals("Restaurant not found", e.getMessage());
        }

        verify(jwtHandlerPort, times(1)).extractDni(token);
        verify(restaurantPersistencePort, times(1)).findByIdOwner(ownerDni);
        verify(restaurantEmployeePersistencePort, never()).saveEmployeeRestaurant(any());
    }
}