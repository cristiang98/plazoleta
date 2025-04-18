package com.plaza.domain.api;

import com.plaza.domain.dto.RestaurantEfficiencyResponseDto;
import com.plaza.domain.model.OrderModel;
import org.springframework.data.domain.Page;


public interface IOrderServicePort {

    void saveOrder(OrderModel orderModel, String token);


    Page<OrderModel> getOrders(int page, int size, String statusFilter, String token);

    void updateOrder(OrderModel orderModel, String token);

    void readyOrder(OrderModel orderModel, String token);

    void deliveryOrder(Integer pin, String token);

    void deleteOrder(Integer idOrder, String token);

    RestaurantEfficiencyResponseDto getEfficiency(String token);
}
