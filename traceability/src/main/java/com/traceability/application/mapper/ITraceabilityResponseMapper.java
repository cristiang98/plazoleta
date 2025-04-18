package com.traceability.application.mapper;

import com.traceability.application.dto.response.TraceabilityResponseDto;
import com.traceability.domain.model.TraceabilityModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ITraceabilityResponseMapper {

    TraceabilityResponseDto toDto(TraceabilityModel traceabilityModel);
}
