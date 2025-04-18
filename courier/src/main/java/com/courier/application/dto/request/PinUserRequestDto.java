package com.courier.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PinUserRequestDto {

    @NotNull
    private Integer userId;
    @NotNull
    private Integer orderId;

}
