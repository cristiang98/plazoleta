package com.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantEmpRequestDto {

    private Integer restaurantNit;
    private Integer employeeId;

}
