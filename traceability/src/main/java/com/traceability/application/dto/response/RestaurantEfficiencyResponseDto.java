package com.traceability.application.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class RestaurantEfficiencyResponseDto {

    @Schema(description = "Tiempo en minutos", example = "10")
    private Long timeInMinutes;
    @Schema(description = "Eficiencia promedio", example = "10")
    private Long averageEfficiency;
    @Schema(description = "Lista de eficiencia de pedidos")
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
