package com.plaza.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PinUserResponseDto {

    private Integer pin;

    private Integer userId;

    private Integer orderId;
}
