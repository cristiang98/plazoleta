package com.user.application.mapper;

import com.user.application.dto.request.UserLoginRequestDto;
import com.user.application.dto.request.UserRequestDto;
import com.user.domain.model.UserLoginModel;
import com.user.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IUserRequestMapper {

    UserModel toUserModel(UserRequestDto userRequestDto);
    UserLoginModel toUserLoginModel(UserLoginRequestDto userLoginRequestDto);

}
