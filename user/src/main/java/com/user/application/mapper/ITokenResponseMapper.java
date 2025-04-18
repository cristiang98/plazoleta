package com.user.application.mapper;

import com.user.application.dto.response.TokenResponseDto;
import com.user.domain.model.TokenModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ITokenResponseMapper {

    TokenResponseDto toTokenResponseDto(TokenModel tokenModel);

}
