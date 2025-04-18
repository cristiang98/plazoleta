package com.traceability.application.handler.impl;

import com.traceability.application.dto.request.OrderModelRequestDto;
import com.traceability.application.dto.request.TraceabilityRequestDto;
import com.traceability.application.dto.response.RestaurantEfficiencyResponseDto;
import com.traceability.application.dto.response.TraceabilityResponseDto;
import com.traceability.application.handler.ITraceabilityHandler;
import com.traceability.application.mapper.IOrderModelRequestMapper;
import com.traceability.application.mapper.IRestaurantEfficencyResponseMapper;
import com.traceability.application.mapper.ITraceabilityRequestMapper;
import com.traceability.application.mapper.ITraceabilityResponseMapper;
import com.traceability.domain.api.ITraceabilityServicePort;
import com.traceability.domain.model.OrderEfficiencyModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TraceabilityHandler implements ITraceabilityHandler {

    private final ITraceabilityServicePort traceabilityServicePort;
    private final ITraceabilityRequestMapper traceabilityRequestMapper;
    private final ITraceabilityResponseMapper traceabilityResponseMapper;
    private final IOrderModelRequestMapper orderModelRequestMapper;
    private final IRestaurantEfficencyResponseMapper restaurantEfficencyResponseMapper;


    @Override
    public void saveTraceability(TraceabilityRequestDto traceabilityRequestDto) {
        traceabilityServicePort.saveTraceability(traceabilityRequestMapper.toModel(traceabilityRequestDto));
    }

    @Override
    public TraceabilityResponseDto findTraceabilityByIdClient(Integer idClient) {
        return traceabilityResponseMapper.toDto(traceabilityServicePort.findTraceabilityByIdClient(idClient));
    }

    @Override
    public RestaurantEfficiencyResponseDto getEfficiency(List<OrderModelRequestDto> orderModelRequestDtos) {
        List<OrderEfficiencyModel> orderEfficiencyModels = orderModelRequestMapper.toModelList(orderModelRequestDtos);
        return restaurantEfficencyResponseMapper.toDto(traceabilityServicePort.getEfficiency(orderEfficiencyModels));
    }


}
