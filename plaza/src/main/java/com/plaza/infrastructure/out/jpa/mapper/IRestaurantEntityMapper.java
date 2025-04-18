package com.plaza.infrastructure.out.jpa.mapper;

import com.plaza.domain.model.RestaurantModel;
import com.plaza.infrastructure.out.jpa.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IRestaurantEntityMapper {

    RestaurantModel toRestaurantModel(RestaurantEntity restaurantEntity);
    RestaurantEntity toRestaurantEntity(RestaurantModel restaurantModel);

}
