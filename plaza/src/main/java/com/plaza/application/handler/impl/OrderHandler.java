package com.plaza.application.handler.impl;

import com.plaza.application.dto.request.OrderRequestDto;
import com.plaza.application.dto.request.UpdateOrderRequestDto;
import com.plaza.application.dto.response.OrderResponseDto;
import com.plaza.application.handler.IOrderHandler;
import com.plaza.application.mapper.IOrderRequestMapper;
import com.plaza.application.mapper.response.IOrderResponseMapper;
import com.plaza.domain.api.IDishServicePort;
import com.plaza.domain.api.IOrderServicePort;
import com.plaza.domain.api.IRestaurantServicePort;
import com.plaza.domain.dto.RestaurantEfficiencyResponseDto;
import com.plaza.domain.model.OrderModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderHandler implements IOrderHandler {

    private final IOrderServicePort orderServicePort;
    private final IDishServicePort dishServicePort;
    private final IRestaurantServicePort restaurantServicePort;
    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderResponseMapper orderResponseMapper;

    @Override
    public void saveOrder(OrderRequestDto orderRequestDto, String token) {

        OrderModel orderModel = orderRequestMapper.toModel(orderRequestDto);
        orderServicePort.saveOrder(orderModel, token);
    }

    @Override
    public Page<OrderResponseDto> getOrders(int page, int size, String statusFilter, String token) {
        return orderServicePort.getOrders(page, size, statusFilter, token).map(orderResponseMapper::toOrderResponseDto);
    }

    @Override
    public void updateOrder(UpdateOrderRequestDto updateOrderRequestDto, String token) {
        OrderModel orderModel = orderRequestMapper.toModelFromUpdate(updateOrderRequestDto);
        orderServicePort.updateOrder(orderModel, token);
    }

    @Override
    public void readyOrder(UpdateOrderRequestDto updateOrderRequestDto, String token) {
        OrderModel orderModel = orderRequestMapper.toModelFromUpdate(updateOrderRequestDto);
        orderServicePort.readyOrder(orderModel, token);
    }

    @Override
    public void deliveryOrder(Integer pin, String token) {
        orderServicePort.deliveryOrder(pin, token);
    }

    @Override
    public void deleteOrder(Integer idOrder, String token) {
        orderServicePort.deleteOrder(idOrder, token);
    }

    @Override
    public RestaurantEfficiencyResponseDto getEfficiency(String token) {
        return orderServicePort.getEfficiency(token);
    }
}
