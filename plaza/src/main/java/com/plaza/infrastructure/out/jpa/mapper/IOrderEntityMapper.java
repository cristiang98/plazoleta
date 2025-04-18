package com.plaza.infrastructure.out.jpa.mapper;

import com.plaza.domain.model.OrderModel;
import com.plaza.domain.model.enums.OrderStatus;
import com.plaza.infrastructure.out.jpa.entity.OrderEntity;
import com.plaza.infrastructure.out.jpa.entity.enums.OrderStatusI;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IOrderEntityMapper {


    OrderEntity toEntity(OrderModel orderModel);

    @Mapping(ignore = true, target = "orderDishes")
    OrderModel toModel(OrderEntity orderEntity);

    List<OrderStatusI> toOrderStatus(List<OrderStatus>  orderModel);

    @Mapping(target = "orderDishes", ignore = true)
    List<OrderModel> toModelList(List<OrderEntity> orderEntities);

}
