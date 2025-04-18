package com.plaza.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantEmployeeResponseDto {

    private Integer id;
    private Integer restaurantNit;
    private Integer employeeId;
}
