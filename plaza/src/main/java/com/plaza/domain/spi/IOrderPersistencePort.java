package com.plaza.domain.spi;

import com.plaza.domain.model.OrderModel;
import com.plaza.domain.model.enums.OrderStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOrderPersistencePort {

    void saveOrder(OrderModel orderModel);

    public Boolean hasPendingOrders(Integer idClient, List<OrderStatus> statusList);

    Page<OrderModel> getOrders(int page, int size, String statusFilter, Integer restaurantNit);

    OrderModel findById(Integer orderId);

    void updateOrder(OrderModel orderModelCurrent);

    void deliveyOrder(OrderModel orderModel);

    void deleteOrder(Integer idOrder);

    List<OrderModel> findAllByRestaurantNit(Integer RestaurantNit);
}
