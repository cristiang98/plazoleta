package com.plaza.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDishResponseDto {
    private Integer id;

    private DishWithoutRestaurantResponseDto dish;

    private Integer quantity;
}
