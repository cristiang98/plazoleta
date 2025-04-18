package com.plaza.application.mapper;

import com.plaza.application.dto.request.OrderDishRequestDto;
import com.plaza.domain.model.OrderDishModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IOrderDishesRequestMapper {

    List <OrderDishModel> toModel(List<OrderDishRequestDto> orderDishRequestDto);

}
