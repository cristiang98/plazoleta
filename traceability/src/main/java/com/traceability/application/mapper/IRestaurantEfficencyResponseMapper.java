package com.traceability.application.mapper;


import com.traceability.application.dto.response.RestaurantEfficiencyResponseDto;
import com.traceability.domain.model.RestaurantEfficiencyModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IRestaurantEfficencyResponseMapper {

    RestaurantEfficiencyResponseDto toDto(RestaurantEfficiencyModel restaurantEfficiencyModel);

}
