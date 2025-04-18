package com.plaza.application.mapper;

import com.plaza.application.dto.request.DishRequestDto;
import com.plaza.application.dto.request.OrderDishRequestDto;
import com.plaza.application.dto.request.UpdateDishRequestDto;
import com.plaza.domain.model.DishModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface IDishRequestMapper {

    DishModel toDishModel(DishRequestDto dishRequestDto);
    DishModel toDishModel(UpdateDishRequestDto updateDishRequestDto);

    List<DishModel> toModel(List<OrderDishRequestDto> dishes);

}
