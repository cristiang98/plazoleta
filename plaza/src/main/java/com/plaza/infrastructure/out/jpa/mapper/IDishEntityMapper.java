package com.plaza.infrastructure.out.jpa.mapper;

import com.plaza.domain.model.DishModel;
import com.plaza.infrastructure.out.jpa.entity.DishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IDishEntityMapper {

    DishModel toDishModel(DishEntity dishEntity);
    DishEntity toDishEntity(DishModel dishModel);

}
