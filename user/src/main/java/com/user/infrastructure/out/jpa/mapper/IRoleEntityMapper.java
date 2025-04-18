package com.user.infrastructure.out.jpa.mapper;

import com.user.domain.model.RoleModel;
import com.user.infrastructure.out.jpa.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IRoleEntityMapper {

    RoleModel toModel(RoleEntity roleEntity);
    RoleEntity toEntity(RoleModel roleModel);

}
