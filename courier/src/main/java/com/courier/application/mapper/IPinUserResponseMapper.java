package com.courier.application.mapper;

import com.courier.application.dto.response.PinUserResponseDto;
import com.courier.domain.model.PinUserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IPinUserResponseMapper {

    PinUserResponseDto toResponseDto(PinUserModel pinUserModel);

}
