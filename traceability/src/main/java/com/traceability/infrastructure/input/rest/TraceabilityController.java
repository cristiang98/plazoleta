package com.traceability.infrastructure.input.rest;

import com.traceability.application.dto.request.OrderModelRequestDto;
import com.traceability.application.dto.request.TraceabilityRequestDto;
import com.traceability.application.dto.response.RestaurantEfficiencyResponseDto;
import com.traceability.application.dto.response.TraceabilityResponseDto;
import com.traceability.application.handler.ITraceabilityHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/traceability")
@RequiredArgsConstructor
public class TraceabilityController {

    private final ITraceabilityHandler traceabilityHandler;

    @Operation(
            summary = "Guardar trazabilidad",
            description = "Este endpoint permite guardar la trazabilidad de un pedido."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "La trazabilidad fue guardada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TraceabilityRequestDto.class))
            )
    })
    @PostMapping("/save")
    public void saveTraceability(@RequestBody TraceabilityRequestDto traceabilityRequestDto) {
        traceabilityHandler.saveTraceability(traceabilityRequestDto);
        System.out.println();
    }

    @Operation(
            summary = "Buscar trazabilidad por id del pedido",
            description = "Este endpoint permite buscar la trazabilidad de un pedido por su id."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "La trazabilidad fue encontrada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TraceabilityResponseDto.class))
            ),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Prohibido")
    })
    @GetMapping("/findByIdClient")
    public TraceabilityResponseDto findTraceabilityByOrderId(@RequestParam Integer idClient) {
        return traceabilityHandler.findTraceabilityByIdClient(idClient);
    }

    @Operation(
            summary = "Calcular eficiencia del restaurante",
            description = "Este endpoint permite calcular la eficiencia del restaurante."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "La eficiencia fue calculada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantEfficiencyResponseDto.class))
            ),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Prohibido")
    })
    @PostMapping("/efficiency")
    public RestaurantEfficiencyResponseDto getEfficiency(@RequestBody List<OrderModelRequestDto> orderModelList) {
        return traceabilityHandler.getEfficiency(orderModelList);
    }

}
