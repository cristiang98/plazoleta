package com.traceability.domain.usecase;

import com.traceability.domain.model.OrderEfficiencyModel;
import com.traceability.domain.model.RestaurantEfficiencyModel;
import com.traceability.domain.model.TraceabilityModel;
import com.traceability.domain.spi.ITraceabilityPersistencePort;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

class TraceabilityUseCaseTest {

    /**
     * Tests the saveTraceability method which is responsible for delegating the save operation
     * for a TraceabilityModel to the persistence layer.
     */
    @Test
    void saveTraceability_ShouldInvokePersistencePortSaveMethod() {
        // Arrange
        ITraceabilityPersistencePort traceabilityPersistencePort = mock(ITraceabilityPersistencePort.class);
        TraceabilityUseCase traceabilityUseCase = new TraceabilityUseCase(traceabilityPersistencePort);

        TraceabilityModel traceabilityModel = new TraceabilityModel();
        traceabilityModel.setId("1");
        traceabilityModel.setIdOrder(101);
        traceabilityModel.setIdClient(202);
        traceabilityModel.setEmailClient("client@example.com");
        traceabilityModel.setDateStart(java.time.LocalDateTime.now());
        traceabilityModel.setStatusCurrent("IN_PROGRESS");

        // Act
        traceabilityUseCase.saveTraceability(traceabilityModel);

        // Assert
        verify(traceabilityPersistencePort, times(1)).saveTraceability(any(TraceabilityModel.class));
    }

    /**
     * Tests the findTraceabilityByIdClient method to ensure it correctly retrieves a TraceabilityModel
     * for a given idClient.
     */
    @Test
    void findTraceabilityByIdClient_ShouldReturnExpectedModel() {
        // Arrange
        ITraceabilityPersistencePort traceabilityPersistencePort = mock(ITraceabilityPersistencePort.class);
        TraceabilityUseCase traceabilityUseCase = new TraceabilityUseCase(traceabilityPersistencePort);

        Integer idClient = 202;
        TraceabilityModel expectedModel = new TraceabilityModel();
        expectedModel.setId("1");
        expectedModel.setIdOrder(101);
        expectedModel.setIdClient(idClient);
        expectedModel.setEmailClient("client@example.com");
        expectedModel.setDateStart(java.time.LocalDateTime.now());
        expectedModel.setStatusCurrent("IN_PROGRESS");

        when(traceabilityPersistencePort.findTraceabilityByIdClient(idClient)).thenReturn(expectedModel);

        // Act
        TraceabilityModel actualModel = traceabilityUseCase.findTraceabilityByIdClient(idClient);

        // Assert
        assertEquals(expectedModel, actualModel);
    }

    /**
     * Verifies that the findTraceabilityByIdClient method calls the persistence port exactly once
     * for a given idClient.
     */
    @Test
    void findTraceabilityByIdClient_ShouldInvokePersistencePortMethod() {
        // Arrange
        ITraceabilityPersistencePort traceabilityPersistencePort = mock(ITraceabilityPersistencePort.class);
        TraceabilityUseCase traceabilityUseCase = new TraceabilityUseCase(traceabilityPersistencePort);

        Integer idClient = 202;

        // Act
        traceabilityUseCase.findTraceabilityByIdClient(idClient);

        // Assert
        verify(traceabilityPersistencePort, times(1)).findTraceabilityByIdClient(idClient);
        verifyNoMoreInteractions(traceabilityPersistencePort);
    }

    /**
     * Verifies that saveTraceability throws no errors for a valid TraceabilityModel.
     */
    @Test
    void saveTraceability_ShouldNotThrowExceptionForValidModel() {
        // Arrange
        ITraceabilityPersistencePort traceabilityPersistencePort = mock(ITraceabilityPersistencePort.class);
        TraceabilityUseCase traceabilityUseCase = new TraceabilityUseCase(traceabilityPersistencePort);

        TraceabilityModel traceabilityModel = new TraceabilityModel();
        traceabilityModel.setId("1");
        traceabilityModel.setIdOrder(101);
        traceabilityModel.setIdClient(202);
        traceabilityModel.setEmailClient("client@example.com");
        traceabilityModel.setDateStart(java.time.LocalDateTime.now());
        traceabilityModel.setStatusCurrent("IN_PROGRESS");

        // Act & Assert
        traceabilityUseCase.saveTraceability(traceabilityModel);

        // Ensure no exceptions are thrown
        verify(traceabilityPersistencePort, times(1)).saveTraceability(traceabilityModel);
        verifyNoMoreInteractions(traceabilityPersistencePort);
    }

    /**
     * Tests the getEfficiency method to ensure it calculates correct total time and average efficiency.
     */
    @Test
    void getEfficiency_ShouldReturnCorrectEfficiencyMetrics() {
        // Arrange
        ITraceabilityPersistencePort traceabilityPersistencePort = mock(ITraceabilityPersistencePort.class);
        TraceabilityUseCase traceabilityUseCase = new TraceabilityUseCase(traceabilityPersistencePort);

        List<OrderEfficiencyModel> orders = new ArrayList<>();
        OrderEfficiencyModel order1 = new OrderEfficiencyModel();
        order1.setId(1);
        order1.setIdEmployee(1);
        orders.add(order1);

        OrderEfficiencyModel order2 = new OrderEfficiencyModel();
        order2.setId(2);
        order2.setIdEmployee(2);
        orders.add(order2);

        // Setup mock behavior
        TraceabilityModel traceability1 = new TraceabilityModel();
        traceability1.setDateStart(LocalDateTime.of(2023, 1, 1, 10, 0));
        traceability1.setDateEnd(LocalDateTime.of(2023, 1, 1, 11, 0));
        when(traceabilityPersistencePort.findTraceabilityByIdOrderAndStatus(1, "ENTREGADO")).thenReturn(traceability1);

        TraceabilityModel traceability2 = new TraceabilityModel();
        traceability2.setDateStart(LocalDateTime.of(2023, 1, 1, 10, 30));
        traceability2.setDateEnd(LocalDateTime.of(2023, 1, 1, 11, 30));
        when(traceabilityPersistencePort.findTraceabilityByIdOrderAndStatus(2, "ENTREGADO")).thenReturn(traceability2);

        // Act
        RestaurantEfficiencyModel result = traceabilityUseCase.getEfficiency(orders);

        // Assert
        assertEquals(120, result.getTimeInMinutes());
        assertEquals(60, result.getAverageEfficiency());
        assertEquals(2, result.getOrdersEfficency().size());
    }

    /**
     * Tests getEfficiency method to verify it correctly handles an empty order list.
     */
    @Test
    void getEfficiency_ShouldHandleEmptyOrderList() {
        // Arrange
        ITraceabilityPersistencePort traceabilityPersistencePort = mock(ITraceabilityPersistencePort.class);
        TraceabilityUseCase traceabilityUseCase = new TraceabilityUseCase(traceabilityPersistencePort);

        List<OrderEfficiencyModel> orders = new ArrayList<>();

        // Act
        RestaurantEfficiencyModel result = traceabilityUseCase.getEfficiency(orders);

        // Assert
        assertEquals(0, result.getTimeInMinutes());
        assertEquals(0, result.getAverageEfficiency());
        assertEquals(0, result.getOrdersEfficency().size());
    }

    /**
     * Tests the getEfficiency method when an order has a null end date.
     */
    @Test
    void getEfficiency_ShouldHandleOrderWithNullEndDate() {
        // Arrange
        ITraceabilityPersistencePort traceabilityPersistencePort = mock(ITraceabilityPersistencePort.class);
        TraceabilityUseCase traceabilityUseCase = new TraceabilityUseCase(traceabilityPersistencePort);

        List<OrderEfficiencyModel> orders = new ArrayList<>();
        OrderEfficiencyModel order = new OrderEfficiencyModel();
        order.setId(1);
        orders.add(order);

        TraceabilityModel traceability = new TraceabilityModel();
        traceability.setDateStart(LocalDateTime.now().minusMinutes(30));
        traceability.setDateEnd(null); // Null end date
        when(traceabilityPersistencePort.findTraceabilityByIdOrderAndStatus(1, "ENTREGADO")).thenReturn(traceability);

        // Act
        RestaurantEfficiencyModel result = traceabilityUseCase.getEfficiency(orders);

        // Assert
        assertEquals(1, result.getOrdersEfficency().size());
        assertEquals(30, result.getOrdersEfficency().get(0).getTimeInMinutes());
    }
}