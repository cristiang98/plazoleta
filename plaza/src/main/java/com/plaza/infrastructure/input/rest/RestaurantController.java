package com.plaza.infrastructure.input.rest;

import com.plaza.application.dto.request.RestaurantRequestDto;
import com.plaza.application.dto.response.RestaurantResponseDto;
import com.plaza.application.handler.IRestaurantHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/plaza")
@RequiredArgsConstructor
public class RestaurantController {

    private final IRestaurantHandler plazaHandler;

    @Operation(summary = "guardar restaurante", description = "Este endpoint permite guardar un restaurante. Solo puede acceder a este endpoint un usuario con rol de administrador")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "El restaurante fue guardado exitosamente"
            ),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Prohibido")
    })
    @PostMapping("/save-restaurant")
    public void savePlaza(@Valid @RequestBody RestaurantRequestDto restaurantRequestDto) {
        plazaHandler.saveRestaurant(restaurantRequestDto);
    }

    @Operation(summary = "obtener restaurantes", description = "Este endpoint permite obtener los restaurantes.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Los restaurantes fueron obtenidos exitosamente"
            ),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Prohibido")
    })

    @GetMapping("/restaurants")
    public Page<RestaurantResponseDto> getRestaurants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return plazaHandler.getRestaurants(page, size);
    }

}
