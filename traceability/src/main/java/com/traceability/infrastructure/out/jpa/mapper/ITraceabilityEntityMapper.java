package com.traceability.infrastructure.out.jpa.mapper;

import com.traceability.domain.model.TraceabilityModel;
import com.traceability.infrastructure.out.jpa.entity.TraceabilityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ITraceabilityEntityMapper {

    TraceabilityEntity toEntity(TraceabilityModel traceabilityModel);
    TraceabilityModel toModel(TraceabilityEntity traceabilityEntity);

}
