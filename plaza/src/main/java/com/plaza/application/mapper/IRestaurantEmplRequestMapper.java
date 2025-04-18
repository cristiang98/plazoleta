package com.plaza.application.mapper;

import com.plaza.application.dto.request.RestaurantEmpRequestDto;
import com.plaza.domain.model.RestaurantEmployeeModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IRestaurantEmplRequestMapper {

    RestaurantEmployeeModel toModel(RestaurantEmpRequestDto restaurantEmpRequestDto);

}
