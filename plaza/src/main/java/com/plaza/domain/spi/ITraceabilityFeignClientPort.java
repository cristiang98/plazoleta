package com.plaza.domain.spi;

import com.plaza.domain.dto.RestaurantEfficiencyResponseDto;
import com.plaza.domain.dto.TraceabilityRequestDto;
import com.plaza.domain.dto.TraceabilityResponseDto;
import com.plaza.domain.model.OrderModel;

import java.util.List;

public interface ITraceabilityFeignClientPort {

    void saveTraceability(TraceabilityRequestDto traceabilityRequestDto);

    TraceabilityResponseDto findTraceabilityByOrderId(Integer idClient);

    RestaurantEfficiencyResponseDto getEfficiency(List<OrderModel> orderModelList);
}
