package com.plaza.domain.model;


public class RestaurantEmployeeModel {

    private Integer restaurantNit;

    private Integer employeeId;


    public RestaurantEmployeeModel(Integer restaurantNit, Integer employeeId) {
        this.restaurantNit = restaurantNit;
        this.employeeId = employeeId;
    }

    public RestaurantEmployeeModel() {
    }

    public Integer getRestaurantNit() {
        return restaurantNit;
    }

    public void setRestaurantNit(Integer restaurantNit) {
        this.restaurantNit = restaurantNit;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }
}
