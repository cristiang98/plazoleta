package com.plaza.application.mapper;

import com.plaza.application.dto.request.RestaurantRequestDto;
import com.plaza.domain.model.RestaurantModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IRestaurantRequestMapper {

    RestaurantModel toRestaurantModel(RestaurantRequestDto restaurantRequestDto);

}
