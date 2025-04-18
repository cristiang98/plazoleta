package com.plaza.domain.dto;

import java.util.List;

public class RestaurantEfficiencyResponseDto {

    private Long timeInMinutes;
    private Long averageEfficiency;
    private List<OrderEfficiencyResponseDto> ordersEfficency;

    public RestaurantEfficiencyResponseDto() {
    }

    public RestaurantEfficiencyResponseDto(Long timeInMinutes, Long averageEfficiency, List<OrderEfficiencyResponseDto> ordersEfficency) {
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

    public List<OrderEfficiencyResponseDto> getOrdersEfficency() {
        return ordersEfficency;
    }

    public void setOrdersEfficency(List<OrderEfficiencyResponseDto> ordersEfficency) {
        this.ordersEfficency = ordersEfficency;
    }
}
