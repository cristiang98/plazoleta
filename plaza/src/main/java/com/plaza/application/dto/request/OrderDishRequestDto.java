package com.plaza.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrderDishRequestDto {

    private Integer idDish;
    private Integer quantity;
}
