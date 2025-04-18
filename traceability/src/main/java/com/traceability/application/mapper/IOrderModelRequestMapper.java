package com.traceability.application.mapper;

import com.traceability.application.dto.request.OrderModelRequestDto;
import com.traceability.domain.model.OrderEfficiencyModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IOrderModelRequestMapper {

    List<OrderEfficiencyModel> toModelList(List<OrderModelRequestDto> orderModelRequestDto);

}
