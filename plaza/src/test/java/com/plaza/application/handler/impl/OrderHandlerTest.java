package com.plaza.application.handler.impl;

import com.plaza.application.dto.request.OrderRequestDto;
import com.plaza.application.dto.request.UpdateOrderRequestDto;
import com.plaza.application.mapper.IOrderRequestMapper;
import com.plaza.application.mapper.response.IOrderResponseMapper;
import com.plaza.domain.api.IDishServicePort;
import com.plaza.domain.api.IOrderServicePort;
import com.plaza.domain.api.IRestaurantServicePort;
import com.plaza.domain.dto.RestaurantEfficiencyResponseDto;
import com.plaza.domain.model.OrderModel;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrderHandlerTest {

    @Mock
    private IOrderServicePort orderServicePort;

    @Mock
    private IDishServicePort dishServicePort;

    @Mock
    private IRestaurantServicePort restaurantServicePort;

    @Mock
    private IOrderRequestMapper orderRequestMapper;

    @Mock
    private  IOrderResponseMapper orderResponseMapper;

    @InjectMocks
    private OrderHandler orderHandler;

    public OrderHandlerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveOrder_Success() {
        // Arrange
        OrderRequestDto mockOrderRequestDto = new OrderRequestDto();
        String mockToken = "mockToken";
        OrderModel mockOrderModel = new OrderModel();

        when(orderRequestMapper.toModel(mockOrderRequestDto)).thenReturn(mockOrderModel);

        // Act
        orderHandler.saveOrder(mockOrderRequestDto, mockToken);

        // Assert
        verify(orderRequestMapper, times(1)).toModel(mockOrderRequestDto);
        verify(orderServicePort, times(1)).saveOrder(mockOrderModel, mockToken);
    }

    @Test
    void testGetOrders_Success() {
        // Arrange
        int page = 0;
        int size = 10;
        String statusFilter = "DELIVERED";
        String token = "mockToken";

        when(orderServicePort.getOrders(page, size, statusFilter, token)).thenReturn(Page.empty());

        // Act
        orderHandler.getOrders(page, size, statusFilter, token);

        // Assert
        verify(orderServicePort, times(1)).getOrders(page, size, statusFilter, token);
    }

    @Test
    void testUpdateOrder_Success() {
        // Arrange
        UpdateOrderRequestDto mockUpdateOrderRequestDto = new UpdateOrderRequestDto();
        String mockToken = "mockToken";
        OrderModel mockOrderModel = new OrderModel();

        when(orderRequestMapper.toModelFromUpdate(mockUpdateOrderRequestDto)).thenReturn(mockOrderModel);

        // Act
        orderHandler.updateOrder(mockUpdateOrderRequestDto, mockToken);

        // Assert
        verify(orderRequestMapper, times(1)).toModelFromUpdate(mockUpdateOrderRequestDto);
        verify(orderServicePort, times(1)).updateOrder(mockOrderModel, mockToken);
    }

    @Test
    void testReadyOrder_Success() {
        // Arrange
        UpdateOrderRequestDto mockUpdateOrderRequestDto = new UpdateOrderRequestDto();
        String mockToken = "mockToken";
        OrderModel mockOrderModel = new OrderModel();

        when(orderRequestMapper.toModelFromUpdate(mockUpdateOrderRequestDto)).thenReturn(mockOrderModel);

        // Act
        orderHandler.readyOrder(mockUpdateOrderRequestDto, mockToken);

        // Assert
        verify(orderRequestMapper, times(1)).toModelFromUpdate(mockUpdateOrderRequestDto);
        verify(orderServicePort, times(1)).readyOrder(mockOrderModel, mockToken);
    }

    @Test
    void testDeliveryOrder_Success() {
        // Arrange
        Integer mockPin = 1234;
        String mockToken = "mockToken";

        // Act
        orderHandler.deliveryOrder(mockPin, mockToken);

        // Assert
        verify(orderServicePort, times(1)).deliveryOrder(mockPin, mockToken);
    }

    @Test
    void testDeleteOrder_Success() {
        // Arrange
        Integer mockOrderId = 1;
        String mockToken = "mockToken";

        // Act
        orderHandler.deleteOrder(mockOrderId, mockToken);

        // Assert
        verify(orderServicePort, times(1)).deleteOrder(mockOrderId, mockToken);
    }

    @Test
    void testGetEfficiency() {
        // GIVEN
        String token = "test-token"; // Token de prueba
        RestaurantEfficiencyResponseDto expectedResponse = new RestaurantEfficiencyResponseDto(); // Simula el DTO esperado

        // Configuración del mock
        when(orderServicePort.getEfficiency(token)).thenReturn(expectedResponse);

        // WHEN
        RestaurantEfficiencyResponseDto result = orderHandler.getEfficiency(token);

        // THEN
        verify(orderServicePort, times(1)).getEfficiency(token);
        assertEquals(expectedResponse, result); // Verifica que el resultado sea el esperado
    }
}