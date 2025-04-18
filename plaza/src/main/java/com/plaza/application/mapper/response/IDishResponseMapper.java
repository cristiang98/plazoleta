package com.plaza.application.mapper.response;

import com.plaza.application.dto.response.DishResponseDto;
import com.plaza.domain.model.DishModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IDishResponseMapper {

    DishResponseDto toDishRequestDto(DishModel dishModel);

}
