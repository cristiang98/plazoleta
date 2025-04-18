package com.plaza.infrastructure.feign.adapter;

import com.plaza.domain.dto.RestaurantEfficiencyResponseDto;
import com.plaza.domain.dto.TraceabilityRequestDto;
import com.plaza.domain.dto.TraceabilityResponseDto;
import com.plaza.domain.model.OrderModel;
import com.plaza.domain.spi.ITraceabilityFeignClientPort;
import com.plaza.infrastructure.out.jpa.feign.ITraceabilityFeignClient;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TraceabilityFeignAdapter implements ITraceabilityFeignClientPort {

    private final ITraceabilityFeignClient traceabilityFeignClient;

    @Override
    public void saveTraceability(TraceabilityRequestDto traceabilityRequestDto) {
        traceabilityFeignClient.saveTraceability(traceabilityRequestDto);
    }

    @Override
    public TraceabilityResponseDto findTraceabilityByOrderId(Integer idClient) {
        return traceabilityFeignClient.findTraceabilityByOrderId(idClient);
    }

    @Override
    public RestaurantEfficiencyResponseDto getEfficiency(List<OrderModel> orderModelList) {
        return traceabilityFeignClient.getEfficiency(orderModelList);
    }
}
