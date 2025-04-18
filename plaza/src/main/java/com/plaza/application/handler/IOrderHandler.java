package com.plaza.application.handler;

import com.plaza.application.dto.request.OrderRequestDto;
import com.plaza.application.dto.request.UpdateOrderRequestDto;
import com.plaza.application.dto.response.OrderResponseDto;
import com.plaza.domain.dto.RestaurantEfficiencyResponseDto;
import org.springframework.data.domain.Page;

public interface IOrderHandler {

    void saveOrder(OrderRequestDto orderRequestDto, String token);

    Page<OrderResponseDto> getOrders(int page, int size, String statusFilter, String token);

    void updateOrder(UpdateOrderRequestDto updateOrderRequestDto, String token);

    void readyOrder(UpdateOrderRequestDto updateOrderRequestDto, String token);

    void deliveryOrder(Integer pin, String token);

    void deleteOrder(Integer idOrder, String token);

    RestaurantEfficiencyResponseDto getEfficiency(String token);
}
