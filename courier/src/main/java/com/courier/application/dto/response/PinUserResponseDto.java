package com.courier.application.dto.response;

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
