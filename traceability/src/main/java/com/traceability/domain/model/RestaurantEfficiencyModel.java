package com.traceability.domain.model;

import java.util.List;

public class RestaurantEfficiencyModel {

    private Long timeInMinutes;
    private Long averageEfficiency;
    private List<OrderEfficiencyModel> ordersEfficency;


    public RestaurantEfficiencyModel() {
    }

    public RestaurantEfficiencyModel(Long timeInMinutes, Long averageEfficiency, List<OrderEfficiencyModel> ordersEfficency) {
        this.timeInMinutes = timeInMinutes;
        this.averageEfficiency = averageEfficiency;
        this.ordersEfficency = ordersEfficency;
    }

    public Long getAverageEfficiency() {
        return averageEfficiency;
    }

    public void setAverageEfficiency(Long averageEfficiency) {
        this.averageEfficiency = averageEfficiency;
    }

    public Long getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(Long timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }

    public List<OrderEfficiencyModel> getOrdersEfficency() {
        return ordersEfficency;
    }

    public void setOrdersEfficency(List<OrderEfficiencyModel> ordersEfficency) {
        this.ordersEfficency = ordersEfficency;
    }
}
