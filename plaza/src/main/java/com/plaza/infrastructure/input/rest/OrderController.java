package com.plaza.infrastructure.input.rest;

import com.plaza.application.dto.request.OrderRequestDto;
import com.plaza.application.dto.request.UpdateOrderRequestDto;
import com.plaza.application.dto.response.OrderResponseDto;
import com.plaza.application.handler.IOrderHandler;
import com.plaza.domain.dto.RestaurantEfficiencyResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderHandler orderHandler;

    @Operation(summary = "guardar orden", description = "Este endpoint permite guardar una orden.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "La orden fue guardada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderRequestDto.class))
            ),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Prohibido")
    })
    @PostMapping("/save-order")
    public void saveOrder(@Valid @RequestBody OrderRequestDto orderRequestDto,
                          @CookieValue String token) {
        orderHandler.saveOrder(orderRequestDto, token);

    }

    @Operation(summary = "obtener ordenes", description = "Este endpoint permite obtener las ordenes. " +
            "Solo puede acceder a este endpoint un usuario con rol de empleado")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Las ordenes fueron obtenidas exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDto.class))
            ),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Prohibido")
    })
    @GetMapping("/orders")
    public Page<OrderResponseDto> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String statusFilter,
            @CookieValue String token
    ) {
        return orderHandler.getOrders(page, size, statusFilter, token);
    }


    @Operation(summary = "actualizar orden", description = "Este endpoint permite actualizar una orden. " +
            "Solo puede acceder a este endpoint un usuario con rol de empleado")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "La orden fue actualizada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateOrderRequestDto.class))
            ),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Prohibido")
    })
    @PatchMapping("/update-order")
    public void updateOrder(
            @RequestBody UpdateOrderRequestDto updateOrderRequestDto,
            @CookieValue String token
    ) {
        orderHandler.updateOrder(updateOrderRequestDto, token);
    }

    @Operation(summary = "orden lista", description = "Este endpoint permite marcar una orden como lista. " +
            "Solo puede acceder a este endpoint un usuario con rol de empleado")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "La orden fue marcada como lista exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateOrderRequestDto.class))
            ),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Prohibido")
    })
    @PatchMapping("/ready-order")
    public void readyOrder(
            @RequestBody UpdateOrderRequestDto updateOrderRequestDto,
            @CookieValue String token
    ) {
        orderHandler.readyOrder(updateOrderRequestDto, token);
    }

    @Operation(summary = "orden entregada", description = "Este endpoint permite marcar una orden como entregada.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "La orden fue marcada como entregada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateOrderRequestDto.class))
            ),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Prohibido")
    })
    @PatchMapping("/delivery-order")
    public void deliveryOrder(
            @RequestParam Integer pin,
            @CookieValue String token
    ) {
        orderHandler.deliveryOrder(pin, token);
    }
    @Operation(summary = "eliminar orden", description = "Este endpoint permite eliminar una orden.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "La orden fue eliminada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateOrderRequestDto.class))
            ),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Prohibido")
    })

    @DeleteMapping("/delete-order")
    public void deleteOrder(
            @RequestParam Integer idOrder,
            @CookieValue String token
    ) {
        orderHandler.deleteOrder(idOrder, token);
    }

    @Operation(summary = "se obtiene el reporte de eficiencia", description = "Este endpoint permite obtener el reporte de eficiencia." +
            "Solo puede acceder a este endpoint un usuario con rol de propietario")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "El reporte de eficiencia fue obtenido exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantEfficiencyResponseDto.class))
            ),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Prohibido")
    })
    @GetMapping("/efficiency")
    public RestaurantEfficiencyResponseDto getEfficiency(
            @CookieValue String token
    ) {
        return orderHandler.getEfficiency(token);
    }



}
