package com.plaza.infrastructure.out.jpa.feign;

import com.plaza.domain.dto.RestaurantEfficiencyResponseDto;
import com.plaza.domain.dto.TraceabilityRequestDto;
import com.plaza.domain.dto.TraceabilityResponseDto;
import com.plaza.domain.model.OrderModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "traceability", path = "/api/v1/traceability")
public interface ITraceabilityFeignClient {

    @PostMapping("/save")
    public void saveTraceability(@RequestBody TraceabilityRequestDto traceabilityRequestDto);

    @GetMapping("/findByIdClient")
    public TraceabilityResponseDto findTraceabilityByOrderId(@RequestParam  Integer idClient);

    @PostMapping("/efficiency")
    RestaurantEfficiencyResponseDto getEfficiency(@RequestBody  List<OrderModel> orderModelList);
}
