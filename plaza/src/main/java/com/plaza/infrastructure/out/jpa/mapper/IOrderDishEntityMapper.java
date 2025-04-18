package com.plaza.infrastructure.out.jpa.mapper;

import com.plaza.domain.model.OrderDishModel;
import com.plaza.infrastructure.out.jpa.entity.OrderDishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IOrderDishEntityMapper {

    @Mapping(target = "order", ignore = true)
    OrderDishModel toModel(OrderDishEntity orderDishEntity);

    @Mapping(target = "order", ignore = true)
    List<OrderDishModel> toModelList(List<OrderDishEntity> orderDishEntities);

}
