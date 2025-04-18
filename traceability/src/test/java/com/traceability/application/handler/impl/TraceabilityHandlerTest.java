package com.traceability.application.handler.impl;

import com.traceability.application.dto.request.OrderModelRequestDto;
import com.traceability.application.dto.request.TraceabilityRequestDto;
import com.traceability.application.dto.response.RestaurantEfficiencyResponseDto;
import com.traceability.application.dto.response.TraceabilityResponseDto;
import com.traceability.application.mapper.IOrderModelRequestMapper;
import com.traceability.application.mapper.IRestaurantEfficencyResponseMapper;
import com.traceability.application.mapper.ITraceabilityRequestMapper;
import com.traceability.application.mapper.ITraceabilityResponseMapper;
import com.traceability.domain.api.ITraceabilityServicePort;
import com.traceability.domain.model.OrderEfficiencyModel;
import com.traceability.domain.model.RestaurantEfficiencyModel;
import com.traceability.domain.model.TraceabilityModel;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TraceabilityHandlerTest {

    @Mock
    private ITraceabilityServicePort traceabilityServicePort;

    @Mock
    private ITraceabilityRequestMapper traceabilityRequestMapper;

    @Mock
    private ITraceabilityResponseMapper traceabilityResponseMapper;

    @Mock
    private IOrderModelRequestMapper orderModelRequestMapper;

    @Mock
    private IRestaurantEfficencyResponseMapper restaurantEfficencyResponseMapper;

    @InjectMocks
    private TraceabilityHandler traceabilityHandler;

    public TraceabilityHandlerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveTraceability() {
        // GIVEN
        TraceabilityRequestDto traceabilityRequestDto = new TraceabilityRequestDto(); // Aquí se debe configurar tu DTO según su implementación
        TraceabilityModel traceabilityModel = new TraceabilityModel(); // Tu modelo de dominio

        // Configuración de mocks
        when(traceabilityRequestMapper.toModel(traceabilityRequestDto)).thenReturn(traceabilityModel);

        // WHEN
        traceabilityHandler.saveTraceability(traceabilityRequestDto);

        // THEN
        verify(traceabilityRequestMapper, times(1)).toModel(traceabilityRequestDto);
        verify(traceabilityServicePort, times(1)).saveTraceability(traceabilityModel);
    }

    @Test
    void testFindTraceabilityByIdClient() {
        // GIVEN
        Integer idClient = 1;
        TraceabilityResponseDto traceabilityResponseDto = new TraceabilityResponseDto(); // Configure your DTO as needed

        // Mock behavior
        when(traceabilityServicePort.findTraceabilityByIdClient(idClient)).thenReturn(new TraceabilityModel()); // Replace with a properly configured TraceabilityModel
        when(traceabilityResponseMapper.toDto(any(TraceabilityModel.class))).thenReturn(traceabilityResponseDto);

        // WHEN
        TraceabilityResponseDto result = traceabilityHandler.findTraceabilityByIdClient(idClient);

        // THEN
        verify(traceabilityServicePort, times(1)).findTraceabilityByIdClient(idClient);
        verify(traceabilityResponseMapper, times(1)).toDto(any(TraceabilityModel.class));
        assertEquals(traceabilityResponseDto, result);
    }

    @Test
    void testGetEfficiency() {
        // GIVEN
        List<OrderModelRequestDto> orderModelRequestDtos = List.of(new OrderModelRequestDto(1, 101, LocalDateTime.now(), 201)); // configure appropriately
        List<OrderEfficiencyModel> orderEfficiencyModels = List.of(new OrderEfficiencyModel()); // configure appropriately
        RestaurantEfficiencyResponseDto restaurantEfficiencyResponseDto = new RestaurantEfficiencyResponseDto();

        // Mock behavior
        when(orderModelRequestMapper.toModelList(orderModelRequestDtos)).thenReturn(orderEfficiencyModels);
        when(traceabilityServicePort.getEfficiency(orderEfficiencyModels))
                .thenReturn(new RestaurantEfficiencyModel(100L, 80L, List.of())); // Provide necessary constructor arguments
        when(restaurantEfficencyResponseMapper.toDto(any())).thenReturn(restaurantEfficiencyResponseDto);

        // WHEN
        RestaurantEfficiencyResponseDto result = traceabilityHandler.getEfficiency(orderModelRequestDtos);

        // THEN
        verify(orderModelRequestMapper, times(1)).toModelList(orderModelRequestDtos);
        verify(traceabilityServicePort, times(1)).getEfficiency(orderEfficiencyModels);
        verify(restaurantEfficencyResponseMapper, times(1)).toDto(any());
        assertEquals(restaurantEfficiencyResponseDto, result);
    }
}