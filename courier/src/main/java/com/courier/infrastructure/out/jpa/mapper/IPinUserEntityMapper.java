package com.courier.infrastructure.out.jpa.mapper;

import com.courier.domain.model.PinUserModel;
import com.courier.infrastructure.out.jpa.entity.PinUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IPinUserEntityMapper {

     PinUserEntity toEntity(PinUserModel pinUserModel);
     PinUserModel toModel(PinUserEntity pinUserEntity);

}
