package com.plaza.domain.model;


import com.plaza.domain.model.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public class OrderModel {

    private Integer id;

    private Integer idClient;

    private LocalDateTime date;

    private RestaurantModel restaurant;

    private OrderStatus status;

    private List<OrderDishModel> orderDishes;

    private Integer idEmployee;

    public OrderModel() {
    }

    public OrderModel(Integer id, Integer idClient, RestaurantModel restaurant, OrderStatus status, List<OrderDishModel> orderDishes) {
        this.id = id;
        this.idClient = idClient;
        this.restaurant = restaurant;
        this.status = status;
        this.orderDishes = orderDishes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public RestaurantModel getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantModel restaurant) {
        this.restaurant = restaurant;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderDishModel> getOrderDishes() {
        return orderDishes;
    }

    public void setOrderDishes(List<OrderDishModel> orderDishes) {
        this.orderDishes = orderDishes;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }
}
