package com.plaza.application.mapper.response;

import com.plaza.application.dto.response.OrderResponseDto;
import com.plaza.domain.model.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IOrderResponseMapper {

    OrderResponseDto toOrderResponseDto(OrderModel orderModel);


}
