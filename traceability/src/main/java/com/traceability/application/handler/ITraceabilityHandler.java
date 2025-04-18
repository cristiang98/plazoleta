package com.traceability.application.handler;

import com.traceability.application.dto.request.OrderModelRequestDto;
import com.traceability.application.dto.request.TraceabilityRequestDto;
import com.traceability.application.dto.response.RestaurantEfficiencyResponseDto;
import com.traceability.application.dto.response.TraceabilityResponseDto;

import java.util.List;

public interface ITraceabilityHandler {

    void saveTraceability(TraceabilityRequestDto traceabilityRequestDto);

    TraceabilityResponseDto findTraceabilityByIdClient(Integer idClient);

    RestaurantEfficiencyResponseDto getEfficiency(List<OrderModelRequestDto> orderModelRequestDtos);
}
