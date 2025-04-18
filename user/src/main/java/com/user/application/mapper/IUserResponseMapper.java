package com.user.application.mapper;

import com.user.application.dto.response.UserResponseDto;
import com.user.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IUserResponseMapper {

    UserResponseDto toUserResponseDto(UserModel userModel);

}
