package com.user.infrastructure.out.jpa.mapper;

import com.user.domain.model.UserModel;
import com.user.infrastructure.out.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IUserEntityMapper {


    UserModel toModel(UserEntity userEntity);
    UserEntity toEntity(UserModel userModel);

}
