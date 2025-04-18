package com.plaza.application.dto.response;


import com.plaza.domain.model.OrderDishModel;
import com.plaza.domain.model.RestaurantModel;
import com.plaza.infrastructure.out.jpa.entity.enums.OrderStatusI;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {

    private Integer id;

    private Integer idClient;

    private LocalDateTime date;

    private RestaurantModel restaurant;

    private OrderStatusI status;

    private Integer idEmployee;

    private List<OrderDishResponseDto> orderDishes;
}
