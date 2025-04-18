package com.plaza.application.mapper.response;

import com.plaza.application.dto.response.RestaurantResponseDto;
import com.plaza.domain.model.RestaurantModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IRestaurantResponseMapper {

    RestaurantResponseDto toRestaurantResponseDto(RestaurantModel restaurantModel);

}
