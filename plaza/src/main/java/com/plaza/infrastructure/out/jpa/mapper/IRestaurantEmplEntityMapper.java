package com.plaza.infrastructure.out.jpa.mapper;

import com.plaza.domain.model.RestaurantEmployeeModel;
import com.plaza.domain.model.RestaurantModel;
import com.plaza.infrastructure.out.jpa.entity.RestaurantEmployeeEntity;
import com.plaza.infrastructure.out.jpa.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IRestaurantEmplEntityMapper {

    RestaurantEmployeeEntity toEntity(RestaurantEmployeeModel restaurantEmployeeModel);
    RestaurantEmployeeModel toModel(RestaurantEmployeeEntity restaurantEmployeeEntity);

}
